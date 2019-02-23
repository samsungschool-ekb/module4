package ru.repast.rxtutorial.observable;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import io.reactivex.*;

public class Observables {


    public static Observable<String> getNum(final Button button) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) throws Exception {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (button.getText() != null && !button.getText().toString().isEmpty()) {
                            emitter.onNext(button.getText().toString());
                        } else {
                          emitter.onNext("Кнопка пустая!!");
                        }
                    }
                });
            }
        });
    }

    /**
     * Работает в течении длительного времени, должен вернуть какой-либо результат
     **/
    public static Observable<String> getObservable(final Button button) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) throws Exception {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i = 0; i < 10; i++) {
                            emitter.onNext("i = " + i);
                        }
                    }
                });

                button.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Log.d("OBSERVABLE", "Завершение работы источника данных");
                        emitter.onComplete();
                        return true;
                    }
                });
            }
        });
    }

    public static Single<String> getSingle(final Button button) {
        return Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(final SingleEmitter<String> emitter) throws Exception {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i = 0; i < 10; i++) {
                            emitter.onSuccess("i = " + i);
                        }
                    }
                });
            }
        });
    }

    public static Completable getCompletable(final Button button) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(final CompletableEmitter emitter) throws Exception {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i = 0; i < 10; i++) {
                            Log.d("SINGLE", "Генерация данных" + i);
                        }
                        emitter.onComplete();
                    }
                });
            }
        });
    }
}
