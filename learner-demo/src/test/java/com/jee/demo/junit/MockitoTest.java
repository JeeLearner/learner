package com.jee.demo.junit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.AdditionalMatchers.lt;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

/**
 *  Mockito测试
 *  暂时脱离springboot容器进行测试
 *
 *  1.Mockito可以模拟任何类或接口
 *      模拟LinkedList和List的实现：
 *          LinkedList mockedList = mock(LinkedList.class);
 *          List list = mock(List.class);
 *
 */

@RunWith(MockitoJUnitRunner.class)
public class MockitoTest {

    @Test
    public void test(){
        //创建mock对象
        CreditSystemService creditService = mock(CreditSystemService.class);
        //模拟mock对象调用，传入任何int值都返回100积分
        when(creditService.getUserCredit(anyInt()))
                .thenReturn(100);
        //实际调用
        int ret = creditService.getUserCredit(10);
        //比较
        assertEquals(100, ret);
    }

    /**
     * 1.模拟方法参数
     */
    @Test
    public void test1(){
        int userId = 10;

        //创建mock对象
        CreditSystemService creditService = mock(CreditSystemService.class);
        //当传入userId=10时返回， 否则报异常
        when(creditService.getUserCredit(eq(userId)))
                .thenReturn(100);
        //实际调用
        int ret = creditService.getUserCredit(10);
        //比较
        assertEquals(100, ret);
    }

    /**
     * 2.verify验证
     *      调用次数
     *      调用顺序
     */
    @Test
    public void test2(){
        int userId = 10;

        //创建mock对象
        CreditSystemService creditService = mock(CreditSystemService.class);
        //模拟mock对象调用，传入任何int值都返回100积分
        when(creditService.getUserCredit(eq(userId))).thenReturn(100);
        //实际调用
        int ret = creditService.getUserCredit(10);
        ret = creditService.getUserCredit(10);

        //2.1次数验证
        assertEquals(100, ret);
        verify(creditService, times(2))
                .getUserCredit(eq(userId));

        creditService.addCedit(userId, ret + 10);

        //2.2验证调用顺序
        InOrder inOrder = inOrder(creditService);
        inOrder.verify(creditService, times(2)).getUserCredit(userId);
        inOrder.verify(creditService).addCedit(userId, ret + 10);
    }

    /**
     * 3.模拟方法返回值
     *   when----thenReturn
     */
    @Test
    public void test3(){
        int userId = 10;

        //创建mock对象
        CreditSystemService creditService = mock(CreditSystemService.class);

        //模拟调用
        //3.1返回结果
        when(creditService.getUserCredit(eq(userId))).thenReturn(100);
        when(creditService.addCedit(eq(userId), anyInt())).thenReturn(true);


        //情况一：3.2抛出异常
        when(creditService.getUserCredit(lt(0))).thenThrow(new IllegalArgumentException("userId不能小于0"));

        int ret = creditService.getUserCredit(-1);
        //验证
        assertEquals(100, ret);

        //情况二：模拟的方法没有返回值，使用doThrow抛出异常
        List list = mock(List.class);
        doThrow(new UnsupportedOperationException("不支持clear方法调用")).when(list).clear();
        //实际调用
        list.clear();
    }
}

