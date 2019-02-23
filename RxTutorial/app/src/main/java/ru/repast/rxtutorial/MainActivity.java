package ru.repast.rxtutorial;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.repast.rxtutorial.observable.Observables;

public class MainActivity extends AppCompatActivity {

    private Button buttonObservable;
    private Button buttonSingle;
    private Button buttonCompletable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();
    }

    private void initUi() {
        buttonObservable = findViewById(R.id.buttonObservable);
        buttonSingle = findViewById(R.id.buttonSingle);
        buttonCompletable = findViewById(R.id.buttonCompletable);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initObservers();
    }

    private void initObservers() {
        Observables.getObservable(buttonObservable)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("OBSERVER", "Подписчик подключился к источнику данных");
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d("OBSERVER", "Данные получены от источника: " + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        Log.d("OBSERVABLE", "Источник данных успешно завершил работу");
                    }
                });

        Observables.getSingle(buttonSingle).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(String s) {
                        Log.d("SINGLE", "Данные получены от источника: " + s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

        Observables.getCompletable(buttonCompletable)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) { }

                    @Override
                    public void onComplete() {
                        Log.d("COMPLETABLE", "Все данные сгенерированы, работаем дальше");
                    }

                    @Override
                    public void onError(Throwable e) { }
                });

        Log.d("ГЛАВНЫЙ ПОТОК", "авыавыаывфафываыфваывф");
    }
}
