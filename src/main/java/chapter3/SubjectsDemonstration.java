package chapter3;

import common.Program;
import rx.Observable;
import rx.Subscription;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

import java.util.concurrent.TimeUnit;

import static common.Helpers.subscribePrint;

public class SubjectsDemonstration implements Program {

    @Override
    public String name() {
        return "Subjects demonstration";
    }

    @Override
    public int chapter() {
        return 3;
    }


    @Override
    public void run() {

        Observable<Long> interval = Observable.interval(100L,
                TimeUnit.MILLISECONDS);

        Subject<Long, Long> publishSubject = PublishSubject.create();
        interval.subscribe(publishSubject);

        Subscription sub1 = subscribePrint(publishSubject, "First");
        Subscription sub2 = subscribePrint(publishSubject, "Second");

        Subscription sub3 = null;
        try {
            Thread.sleep(300L);

            publishSubject.onNext(555L);
            sub3 = subscribePrint(publishSubject, "Third");
            Thread.sleep(500L);
        } catch (InterruptedException e) {
        }

        sub1.unsubscribe();
        sub2.unsubscribe();
        sub3.unsubscribe();

        try {
            Thread.sleep(500L);
        } catch (InterruptedException e) {
        }

        Subscription sub4 = subscribePrint(publishSubject, "Fourth");

        try {
            Thread.sleep(500L);
        } catch (InterruptedException e) {
        }

        sub4.unsubscribe();

        System.out.println("-----------------------------");

    }

    public static void main(String[] args) {
        new SubjectsDemonstration().run();
    }
}
