package com.example.superadapterwrapper.moudle.service.server;

import android.os.IInterface;
import android.os.RemoteException;

import com.example.superadapterwrapper.moudle.service.Book;

import java.util.List;

/**
 * ClassName BookManager
 * User: zuoweichen
 * Date: 2023/2/10 14:17
 * Description: 描述
 * 这个类用来定义服务端 RemoteService 具备什么样的能力
 */
public interface BookManager extends IInterface {
    void addBook(Book book) throws RemoteException;
    List<Book> getBooks() throws RemoteException;
}
