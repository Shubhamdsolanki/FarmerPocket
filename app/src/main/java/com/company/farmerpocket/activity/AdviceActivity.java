package com.company.farmerpocket.activity;

import android.text.InputType;
import android.text.TextUtils;
import android.widget.EditText;

import com.company.farmerpocket.R;
import com.company.farmerpocket.component.SweetAlert.SweetAlertDialog;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 意见反馈页面
 */
public class AdviceActivity extends AbsBaseActivity {

    @Bind(R.id.met_advice_content)
    EditText editTextContent;
    @Bind(R.id.met_advice_contact)
    EditText editTextContact;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_advice;
    }

    @Override
    protected String setToolBarTitle() {
        return "意见反馈";
    }

    @Override
    protected boolean isOpenSwipeBack() {
        return true;
    }

    @Override
    protected void init() {
        //设置editText自动换行
        editTextContent.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        editTextContent.setSingleLine(false);
        editTextContent.setHorizontallyScrolling(false);
    }

    /**
     * 提交反馈
     */
    @OnClick(R.id.btn_advice_commit)
    public void aviceCommit() {
        if (TextUtils.isEmpty(editTextContent.getText())) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("说点什么吧")
                    .setContentText("最美的不是下雨天，是与你一起躲过雨的屋檐")
                    .setConfirmText("我知道了")
                    .show();
            return;
        }
        if (TextUtils.isEmpty(editTextContact.getText())) {
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("感觉缺点什么")
                    .setContentText("输入您的联系方式，哪怕是假的也好")
                    .setConfirmText("我知道了")
                    .show();
            return;
        }
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("反馈已提交")
                .setContentText("感谢您的反馈，我们会做的更好")
                .setConfirmText("不客气啦")
                .show();
        editTextContent.setText("");
        editTextContact.setText("");
    }
}
