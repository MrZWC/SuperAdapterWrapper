package com.example.superadapterwrapper;


interface IPersonAidlInterface {
   void setName(String name);
   void setAge(int age);
   String getInfo();
   void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
}