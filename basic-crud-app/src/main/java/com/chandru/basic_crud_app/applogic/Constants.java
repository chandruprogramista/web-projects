package com.chandru.basic_crud_app.applogic;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Component
public class Constants {

    @Value("${train.start}")
    private String start;

    @Value("${train.end}")
    private String end;

    @Value("${train.count.seats}")
    private int total_seat_count;

    @Value("${train.count.wait}")
    private int total_wait_count;

    public static String START;
    public static String END;
    public static int TOTAL_SEAT_COUNT;
    public static int TOTAL_WAIT_COUNT;

    @PostConstruct
    public void construct () {
        START = this.start;
        END = this.end;
        TOTAL_SEAT_COUNT = this.total_seat_count;
        TOTAL_WAIT_COUNT = this.total_wait_count;
    }

    public static <T, N> BiConsumer<T, N> ifTrueOrElse (Predicate<T> condition, Consumer<N> ifTrue, Consumer<N> orElse) {
        Objects.requireNonNull(ifTrue);
        Objects.requireNonNull(orElse);
        return (T i, N v) -> {
            if (condition.test(i))
                ifTrue.accept(v);
            else
                orElse.accept(v);
        };
    }
}
