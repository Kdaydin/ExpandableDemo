package com.khomeapps.expandablelistactivty

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import com.khomeapps.expandablelistactivty.databinding.ItemExpandableBinding

class ExpandablelistAdapter(val map: Map<String, List<String>>) : BaseExpandableListAdapter() {
    override fun getGroupCount(): Int = map.size

    override fun getChildrenCount(groupPosition: Int): Int =
        map[map.keys.elementAt(groupPosition)]?.size ?: 0

    override fun getGroup(groupPosition: Int): Any = map.keys.elementAt(groupPosition)

    override fun getChild(groupPosition: Int, childPosition: Int): Any =
        map[map.keys.elementAt(groupPosition)] ?: listOf<String>()

    override fun getGroupId(groupPosition: Int): Long = groupPosition.toLong()

    override fun getChildId(groupPosition: Int, childPosition: Int): Long = childPosition.toLong()

    override fun hasStableIds(): Boolean = true

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val binding =
            ItemExpandableBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
        binding.expandedListItem.text = map.keys.elementAt(groupPosition)
        return binding.root
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val binding =
            ItemExpandableBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
        binding.expandedListItem.text =
            map[map.keys.elementAt(groupPosition)]?.get(childPosition) ?: ""
        return binding.root
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean = true
}