package com.example.superadapterwrapper.moudle;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.superadapterwrapper.R;
import com.example.superadapterwrapper.adapter.HappyAdapter;
import com.example.superadapterwrapper.base.BaseActivity;
import com.example.superadapterwrapper.common.Constants;
import com.example.superadapterwrapper.util.DeflaterUtils;
import com.example.superadapterwrapper.util.DensityUtils;
import com.example.superadapterwrapper.util.FileUtils;
import com.example.superadapterwrapper.util.RDes;
import com.idonans.lang.util.ToastUtil;
import com.socks.library.KLog;

import java.io.File;
import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HappyActivity extends BaseActivity {
    private TextView encryption;//加密
    private TextView decrypt;//解密
    private RecyclerView mRecyclerView;

    private HappyAdapter mAdapter;
    private ArrayList<File> mList = new ArrayList<>();
    private String startString = "zzcc";

    public static void start(Context context) {
        Intent intent = new Intent(context, HappyActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_happy);

    }

    @Override
    protected void initView() {
        mRecyclerView = getView(R.id.mRecyclerView);
        encryption = getView(R.id.encryption);
        decrypt = getView(R.id.decrypt);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new HappyAdapter(getContext(), mList);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = DensityUtils.dp2px(HappyActivity.this, 15);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        startSearch();
    }

    @Override
    protected void initLinstener() {

        encryption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show("加密开始");
                startEncrypt();
            }
        });
        decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show("解密开始");
                startDecrypt();
            }
        });
    }

    private void startSearch() {
        Observable.just("").map(new Function<String, ArrayList<File>>() {
            @Override
            public ArrayList<File> apply(String s) throws Throwable {
                ArrayList<File> fileList = FileUtils.getFileList(FileUtils.getRootDirectory());
                return fileList;
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ArrayList<File>>() {
                    @Override
                    public void accept(ArrayList<File> fileArrayList) throws Throwable {
                        mList.clear();
                        mList.addAll(fileArrayList);
                        mAdapter.notifyDataSetChanged();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        throwable.printStackTrace();
                    }
                });
    }

    private final String replcaeKey = "slash";

    private void startEncrypt() {
        Observable.just(mList)
                .map(fileArrayList -> {
                    for (File file : fileArrayList) {
                        if (file.getName().startsWith(startString)) {
                            continue;
                        } else {
                            String name = file.getName();
                            String filename = name.replaceAll("[\\s*\\r\\n]", "");
                            String encrypt = RDes.encrypt(filename, Constants.cryptKey);
                            encrypt = DeflaterUtils.zipString(encrypt);
                            encrypt = encrypt.replaceAll("/", replcaeKey);
                            encrypt = FileUtils.getRootDirectory() + "/" + startString + encrypt;
                            File file1 = FileUtils.renameFile(file, encrypt);
                            KLog.d("加密", file1.getAbsolutePath());
                        }
                    }
                    return fileArrayList;
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe((Consumer<Object>) o -> {
                    startSearch();
                    ToastUtil.show("加密结束");
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        throwable.printStackTrace();
                        ToastUtil.show("加密错误");
                    }
                });
    }

    private void startDecrypt() {
        Observable.just(mList)
                .map(fileArrayList -> {
                    for (File file : fileArrayList) {
                        if (file.getName().startsWith(startString)) {
                            String filename = file.getName().replaceAll("[\\s*\\r\\n]", "");
                            String replace = filename.replace(startString, "");
                            replace = replace.replaceAll(replcaeKey, "/");
                            replace = DeflaterUtils.unzipString(replace);
                            String encrypt = RDes.decrypt(replace, Constants.cryptKey);
                            encrypt = FileUtils.getRootDirectory() + "/" + encrypt;
                            File file1 = FileUtils.renameFile(file, encrypt);
                            KLog.d("解密", file1.getAbsolutePath());
                        }
                    }
                    return fileArrayList;
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe((Consumer<Object>) o -> {
                    startSearch();
                    ToastUtil.show("解密结束");
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        throwable.printStackTrace();
                        ToastUtil.show("解密错误");
                    }
                });
    }
}
