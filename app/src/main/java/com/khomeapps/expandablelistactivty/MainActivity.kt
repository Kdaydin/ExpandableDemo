package com.khomeapps.expandablelistactivty

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ExpandableListView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.khomeapps.expandablelistactivty.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val data = hashMapOf(
        Pair("Title - A ", listOf("A", "B", "C")),
        Pair("Title - B ", listOf("A", "B", "C", "D")),
        Pair("Title - C ", listOf("A", "B", "C", "D", "E")),
        Pair("Title - D ", listOf("A", "B")),
        Pair("Title - E ", listOf("A", "B", "C", "D", "E", "F", "G")),
        Pair("Title - F ", listOf("A", "B", "C")),
        Pair("Title - G ", listOf("A")),
        Pair("Title - H ", listOf("A", "B"))
    )

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.expandableListView.setAdapter(ExpandablelistAdapter(data))
        binding.expandableListView.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {

                setListViewHeight(binding.expandableListView, -1)
                binding.expandableListView.viewTreeObserver.removeOnGlobalLayoutListener(this)

            }
        })

        binding.expandableListView.setOnGroupExpandListener {
            setListViewHeight(binding.expandableListView, it)
        }

        binding.expandableListView.setOnGroupCollapseListener {
            setListViewHeight(binding.expandableListView, it)
        }
    }

    fun setListViewHeight(expList: ExpandableListView, group: Int) {
        val adapter = expList.expandableListAdapter
        var totalHeight = 0;
        val desiredWidth =
            View.MeasureSpec.makeMeasureSpec(expList.width, View.MeasureSpec.UNSPECIFIED)

        for (i in 0 until adapter.groupCount) {
            val groupItem = adapter.getGroupView(i, false, null, expList)
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)

            totalHeight += groupItem.measuredHeight

            if (group != -1 && expList.isGroupExpanded(i)) {
                for (j in 0 until adapter.getChildrenCount(i)) {
                    val listItem = adapter.getChildView(i, j, false, null, expList)
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)
                    totalHeight += listItem.measuredHeight
                }
            }
        }

        val params = expList.layoutParams
        params.height = totalHeight + (expList.dividerHeight * (adapter.groupCount - 1))
        expList.layoutParams = params
        expList.requestLayout()
    }
}