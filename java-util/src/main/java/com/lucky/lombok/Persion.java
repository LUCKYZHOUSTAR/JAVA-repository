package com.lucky.lombok;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 16:02 2017/12/12
 */


@Getter
@Setter
@AllArgsConstructor
@ToString(exclude = {"id"})
public class Persion {

    private long id;
    private String name;


    public static void main(String[] args) {
        Persion persion = new Persion(23, "asdf");
        System.out.println(persion);
    }


    @Override
    public int hashCode() {

        int hash = 31;
        hash = LangUtils.hashCode(hash, name);
        hash = LangUtils.hashCode(hash, id);
        return hash;

    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (this == obj) {
            return true;
        } else if (obj instanceof Persion) {
            return true;
        } else {
            Persion persion = (Persion) obj;
            return LangUtils.equals(this.name, ((Persion) obj).name) && LangUtils.equals(this.id, ((Persion) obj).id);
        }


    }
}
