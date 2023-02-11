package com.example.superadapterwrapper.moudle.service.proxy;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import com.example.superadapterwrapper.moudle.service.Book;
import com.example.superadapterwrapper.moudle.service.server.BookManager;
import com.example.superadapterwrapper.moudle.service.server.Stub;

import java.util.List;

/**
 * ClassName Proxy
 * User: zuoweichen
 * Date: 2023/2/10 14:37
 * Description: 描述
 */
public class Proxy implements BookManager {
    private static final String DESCRIPTOR = "com.example.superadapterwrapper.BookManager";
    private IBinder remote;

    public Proxy(IBinder binder) {
        this.remote = binder;
    }

    @Override
    public void addBook(Book book) throws RemoteException {

        Parcel data = Parcel.obtain();
        Parcel replay = Parcel.obtain();

        try {
            data.writeInterfaceToken(DESCRIPTOR);
            if (book != null) {
                data.writeInt(1);
                book.writeToParcel(data, 0);
            } else {
                data.writeInt(0);
            }
            remote.transact(Stub.TRANSAVTION_addBook, data, replay, 0);
            replay.readException();
        } finally {
            replay.recycle();
            data.recycle();
        }
    }

    @Override
    public List<Book> getBooks() throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel replay = Parcel.obtain();
        List<Book> result;

        try {
            data.writeInterfaceToken(DESCRIPTOR);
            remote.transact(Stub.TRANSAVTION_getBooks, data, replay, 0);
            replay.readException();
            result = replay.createTypedArrayList(Book.CREATOR);
        } finally {
            replay.recycle();
            data.recycle();
        }
        return result;
    }

    @Override
    public IBinder asBinder() {
        return remote;
    }
}
