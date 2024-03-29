package com.example.superadapterwrapper.moudle.service.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.superadapterwrapper.moudle.service.Book;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName RemoteService
 * User: zuoweichen
 * Date: 2023/2/10 14:17
 * Description: 描述
 */
public class RemoteService extends Service {
    private String TAG=this.getClass().getSimpleName();
    private List<Book> books = new ArrayList<>();

    public RemoteService() {
    }

    @Override
    public void onCreate() {
        KLog.i(TAG,"onCreate");
        super.onCreate();
        Book book = new Book();
        book.setName("三体");
        book.setPrice(88);
        books.add(book);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        KLog.i(TAG,"onBind");
        return bookManager;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        KLog.i(TAG,"onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        KLog.i(TAG,"onRebind");
        super.onRebind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        KLog.i(TAG,"onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        KLog.i(TAG,"onDestroy");
        super.onDestroy();
    }

    private final Stub bookManager = new Stub() {
        @Override
        public List<Book> getBooks() throws RemoteException {
            synchronized (this) {
                if (books != null) {
                    return books;
                }
                return new ArrayList<>();
            }
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            synchronized (this) {
                if (books == null) {
                    books = new ArrayList<>();
                }

                if (book == null)
                    return;

                book.setPrice(book.getPrice() * 2);
                books.add(book);

                Log.e("Server", "books: " + book.toString());
            }
        }
    };
}