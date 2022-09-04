package com.example.template.support.manager;

import android.app.Dialog;

import java.util.Stack;

/**
 * Created by Horrarndoo on 2020/10/30.
 * <p>
 * DialogManager 管理dialog栈
 */

public class DialogManager {
    private static Stack<Dialog> dialogStack;
    private static volatile DialogManager instance;

    private DialogManager() {
        if (dialogStack == null) {
            dialogStack = new Stack<Dialog>();
        }
    }

    /**
     * 单一实例
     */
    public static DialogManager get() {
        if (instance == null) {
            synchronized (DialogManager.class) {
                if (instance == null)
                    instance = new DialogManager();
            }
        }
        return instance;
    }

    /**
     * 添加Dialog到堆
     */
    public void addDialog(Dialog dialog) {
        if (dialogStack == null) {
            dialogStack = new Stack<Dialog>();
        }
        dialogStack.add(dialog);
    }

    /**
     * 移除堆栈中的Dialog
     */
    public void removeDialog(Dialog dialog) {
        if (dialog != null) {
            dialogStack.remove(dialog);
        }
    }

    /**
     * 获取当前Dialog（堆栈中最后一个压入的）
     */
    public Dialog currentDialog() {
        return dialogStack.lastElement();
    }

    /**
     * 结束当前Dialog（堆栈中最后一个压入的）
     */
    public void dismissDialog() {
        Dialog dialog = dialogStack.lastElement();
        dismissDialog(dialog);
    }

    /**
     * 结束指定的Dialog
     */
    public void dismissDialog(Dialog dialog) {
        if (dialog != null) {
            dialogStack.remove(dialog);
            dialog.dismiss();
        }
    }

    /**
     * 结束指定类名的Dialog
     */
    public void dismissDialog(Class<?> cls) {
        for (Dialog dialog : dialogStack) {
            if (dialog.getClass().equals(cls)) {
                dismissDialog(dialog);
            }
        }
    }

    /**
     * 结束所有Dialog
     */
    public void dismissAllDialog() {
        for (int i = 0, size = dialogStack.size(); i < size; i++) {
            if (null != dialogStack.get(i)) {
                dialogStack.get(i).dismiss();
            }
        }
        dialogStack.clear();
    }
}
