package com.example.android.erawanthai;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.RemoteViews;

import androidx.lifecycle.Observer;
import androidx.room.Query;
import androidx.room.Room;

import com.example.android.erawanthai.db.CartItem;
import com.example.android.erawanthai.db.ErawanDatabase;

import java.util.List;

public class ErawanWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        new QueryAsyncTask(context, appWidgetManager, appWidgetIds).execute();
    }

    public class QueryAsyncTask extends AsyncTask<Void, Void, List<CartItem>> {
        Context context;
        AppWidgetManager appWidgetManager;
        int[] appWidgetIds;

        public QueryAsyncTask(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
            this.context = context;
            this.appWidgetManager = appWidgetManager;
            this.appWidgetIds = appWidgetIds;
        }

        @Override
        protected List<CartItem> doInBackground(Void... voids) {
            ErawanDatabase db = ErawanDatabase.getDatabase(context);
            List<CartItem> cartItems = db.cartItemDao().getAllSync();
            return cartItems;
        }

        @Override
        protected void onPostExecute(List<CartItem> cartItems) {
            final int count = appWidgetIds.length;

            for (int i = 0; i < count; i++) {
                int widgetId = appWidgetIds[i];

                RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                        R.layout.widget);

                int total = 0;
                for (CartItem c : cartItems) {
                    total += c.price * c.quantity;
                    System.out.println(total);
                }

                remoteViews.setTextViewText(R.id.cart_total_amount, Utils.formatPrice(total));

                Intent intent = new Intent(context, ErawanWidgetProvider.class);
                intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                        0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                appWidgetManager.updateAppWidget(widgetId, remoteViews);
            }
        }
    }
}
