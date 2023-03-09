// ITestInterface.aidl
package com.example.superadapterwrapper;

// Declare any non-default types here with import statements

interface ITestInterface {

    void registerCalback(in com.example.superadapterwrapper.ITestCallBack callback);
}