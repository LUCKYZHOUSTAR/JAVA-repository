package com.lucky.util;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

import java.io.Serializable;

import static java.lang.System.out;

/**
 * 3. 对齐性填充：所有对象都是8字节对齐的 -> 也就是说，所有对象的起始位置都是满足A（A%8==0），所以对于有的对象需要这个对齐性填充来满足这个规则。
 */
public class Item implements Serializable {


  public static void main(String[] args) {
    out.println(VM.current().details());
    out.println(ClassLayout.parseClass(Item.class).toPrintable());
    out.println(ClassLayout.parseClass(Long.class).toPrintable());

  }
  /**
   * ID
   */
  private final Long id;

  /**
   * 库存
   */
  private int amount;

  public Item(Long id, int amount) {
    this.id = id;
    this.amount = amount;
  }

  public Long getId() {
    return id;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  /**
   * 减库存，如果库存不足，则扣减失败
   *
   * @return
   */
  public boolean decreaseAmount() {

    if (!hasRemaining()) {
      return false;
    }
    amount--;
    return true;

  }

  /**
   * 是否还有库存
   *
   * @return
   */
  public boolean hasRemaining() {
    return amount > 0;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("amount", amount)
        .toString();
  }

}
