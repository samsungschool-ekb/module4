package ru.repast.rxtutorial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.repast.rxtutorial.observable.Observables;

public class Main2Activity extends AppCompatActivity {

    private Button button1;
    private Button button2;
    private Button buttonEmpty;
    private TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initUi();
        initObservers();
    }

    private void initUi() {
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        buttonEmpty = findViewById(R.id.buttonEmpty);
        display = findViewById(R.id.display);
    }

    private void initObservers() {
        Observables
                .getNum(button1)
                .mergeWith(Observables.getNum(button2))
                .mergeWith(Observables.getNum(buttonEmpty))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        display.append(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(Main2Activity.this, "Ошибка " + e, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
