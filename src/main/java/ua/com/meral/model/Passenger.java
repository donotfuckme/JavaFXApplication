package ua.com.meral.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Passenger {

    private int id;

    private boolean survived;

    private int pClass;

    private String name;

    private String sex;

    private float age;

    private int sibSp;

    private int parch;

    private String ticket;

    private float fare;

    private String cabin;

    private String embarked;
}
