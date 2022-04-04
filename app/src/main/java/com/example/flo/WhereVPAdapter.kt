package com.example.flo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter

class WhereVPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> AllofcountryFragment()
            1 -> InsidecountryFragment()
            else -> OutsidecountryFragment()
        }
    }

}

/*class WhereVPAdapter(private val images:List<Int>):RecyclerView.Adapter<WhereVPAdapter.ViewPagerViewHolder>(){

    inner class ViewPagerViewHolder(allofcountryView: View) : RecyclerView.ViewHolder(allofcountryView){
        val imageView:ImageView=allofcountryView.findViewById(R.id.home_allofcountry_layout)
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WhereVPAdapter.ViewPagerViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.fragment_allofcountry,parent,false)
        return ViewPagerViewHolder(view)
    }



    override fun onBindViewHolder(holder: WhereVPAdapter.ViewPagerViewHolder, position: Int) {
        val curImage=images[position]
        holder.imageView.setImageResource(curImage)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }


}*/
