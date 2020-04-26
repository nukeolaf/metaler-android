package com.bnvs.metaler.ui.jobinput

import android.content.Intent
import android.widget.EditText
import com.bnvs.metaler.BasePresenter
import com.bnvs.metaler.BaseView
import com.bnvs.metaler.data.user.certification.model.LoginRequest

interface ContractJobInput {
    interface View: BaseView<Presenter> {
        fun showStudent()

        fun showExpert()

        fun showNothing()

        fun showCompany()

        fun showFounded()

        fun showFreelancer()

        fun showCompleteButton()

        fun showEmptyTextDialog()

        fun showJoinCompleteDialog()

        fun showHomeUi()
    }

    interface Presenter: BasePresenter {
        fun getAddUserRequest(intent: Intent)

        fun openStudent()

        fun openExpert()

        fun openNothing()

        fun openCompany()

        fun openFounded()

        fun openFreelancer()

        fun getSelectedJob(): String

        fun getSelectedJobType(): String

        fun getLastSelectedJobType(): String

        fun completeJobInput(jobTypeInput: String, jobDetailInput: String)

        fun setUserRequest()

        fun addUser()

        fun login(request: LoginRequest)

        fun makeLoginRequest(token: String): LoginRequest

        fun getString(editText: EditText): String
    }
}