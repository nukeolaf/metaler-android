package com.example.metaler_android.materials

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.metaler_android.detail.ActivityDetail
import com.example.metaler_android.home.ActivityHome
import com.example.metaler_android.R
import kotlinx.android.synthetic.main.activity_home.homeBtn
import kotlinx.android.synthetic.main.activity_materials.*

class ActivityMaterials : AppCompatActivity(), ContractMaterials.View {

    private val TAG = "ActivityMaterials"

    override lateinit var presenter: ContractMaterials.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_materials)

        presenter = PresenterMaterials(
            this@ActivityMaterials,
            this@ActivityMaterials
        )

        //탭바의 각 버튼에 맞는 액티비티로 이동하는 클릭 리스너
        homeBtn.setOnClickListener {
            val goToHome = Intent(this, ActivityHome::class.java)
            startActivity(goToHome)
        }

        //카테고리 버튼 색상 초기화. 재료탭은 전체카테고리가 눌린 상태로 시작됨
        inactiveCategoryBtn()
        activeCategoryBtn(allBtn)


    }

    override fun onResume() {
        super.onResume()

        allBtn.setOnClickListener {
            inactiveCategoryBtn()
            activeCategoryBtn(allBtn)
        }
        cooperBtn.setOnClickListener {
            inactiveCategoryBtn()
            activeCategoryBtn(cooperBtn)
        }
        stainlessBtn.setOnClickListener {
            inactiveCategoryBtn()
            activeCategoryBtn(stainlessBtn)

            //상세페이지 레이아웃보려고 테스트용으로 추가함
            val goToDetail = Intent(this, ActivityDetail::class.java)
            startActivity(goToDetail)

        }
        aluminiumBtn.setOnClickListener {
            inactiveCategoryBtn()
            activeCategoryBtn(aluminiumBtn)
        }
        nickelBtn.setOnClickListener {
            inactiveCategoryBtn()
            activeCategoryBtn(nickelBtn)
        }
        steelBtn.setOnClickListener {
            inactiveCategoryBtn()
            activeCategoryBtn(steelBtn)
        }
        toolsBtn.setOnClickListener {
            inactiveCategoryBtn()
            activeCategoryBtn(toolsBtn)
        }
        chemicalBtn.setOnClickListener {
            inactiveCategoryBtn()
            activeCategoryBtn(chemicalBtn)
        }
        othersBtn.setOnClickListener {
            inactiveCategoryBtn()
            activeCategoryBtn(othersBtn)
        }
    }

    override fun showCategories() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showPosts() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showPostDetailUi() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showSearchUi() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showSearchTags() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearSearchTagBar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteSearchTag() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setTapBarListener(context: Context) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    //카테고리 버튼 뷰 속성을 비활성화 상태로 초기화하는 메소드
    private fun inactiveCategoryBtn() {
        var categoryBtnArr  = arrayOf(allBtn,cooperBtn,stainlessBtn,aluminiumBtn,nickelBtn,steelBtn,toolsBtn,chemicalBtn,othersBtn)

        for (i in categoryBtnArr){
            i.setBackgroundResource(0)
            i.setTextColor(ContextCompat.getColor(this,
                R.color.colorLightGrey
            ))
        }
    }

    //눌린 카테고리 버튼의 뷰 속성을 활성화 상태로 변경하는 메소드
    private fun activeCategoryBtn(categoryBtn : TextView){
        categoryBtn.background = ContextCompat.getDrawable(this,
            R.drawable.active_bar
        )
        categoryBtn.setTextColor(ContextCompat.getColor(this,
            R.color.colorPurple
        ))
    }
}
