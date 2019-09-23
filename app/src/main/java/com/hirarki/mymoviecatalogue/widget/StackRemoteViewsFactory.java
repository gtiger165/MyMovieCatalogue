package com.hirarki.mymoviecatalogue.widget;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.hirarki.mymoviecatalogue.R;
import com.hirarki.mymoviecatalogue.database.FavMovieHelper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context context;
    private List<String> widgetPoster = new ArrayList<>();
    private FavMovieHelper helper;
    private WeakReference<FavMovieHelper> weakReference = new WeakReference<>(helper);

    public StackRemoteViewsFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
        helper = FavMovieHelper.getInstance(context.getApplicationContext());
        helper.open();
    }

    @Override
    public void onDataSetChanged() {
        final long identityToken = Binder.clearCallingIdentity();
        widgetPoster = helper.selectFavPoster();
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {
        helper.close();
    }

    @Override
    public int getCount() {
        return widgetPoster.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_item);

        try {
            RequestOptions options = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .fitCenter();

            Bitmap bitmap = Glide.with(context)
                    .asBitmap()
                    .load(widgetPoster.get(i))
                    .apply(options)
                    .submit()
                    .get();

            views.setImageViewBitmap(R.id.image_view, bitmap);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        Bundle bundleExtras = new Bundle();
        bundleExtras.putInt(FavoriteBannerWidget.EXTRA_ITEM, i);
        Intent fillIntent = new Intent();
        fillIntent.putExtras(bundleExtras);
        views.setOnClickFillInIntent(R.id.image_view, fillIntent);
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
