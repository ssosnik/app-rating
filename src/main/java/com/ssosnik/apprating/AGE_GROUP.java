package com.ssosnik.apprating;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AGE_GROUP {

    AGE_GROUP_1(0, 19),
    AGE_GROUP_2(20, 26),
    AGE_GROUP_3(27, 35),
    AGE_GROUP_4(36, 45),
    AGE_GROUP_5(46, 55),
    AGE_GROUP_6(56, 65),
    AGE_GROUP_7(65, 1000);

    private final int from;
    private final int to;

    public boolean isInRange(int age) {
        return age >= from && age <= to;
    }

//    @Override
//    public String toString() {
//        return to > 100 ?
//                String.format("%d+", from) :
//                String.format("%d-%d", from, to);
//    }

}
