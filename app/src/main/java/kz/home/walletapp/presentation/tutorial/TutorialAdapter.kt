package kz.home.walletapp.presentation.tutorial

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.NonDisposableHandle.parent
import kz.home.walletapp.R
import kz.home.walletapp.domain.model.Tutorial

class TutorialAdapter : RecyclerView.Adapter<TutorialViewHolder>() {
    private val tutorialList = mutableListOf<Tutorial>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TutorialViewHolder =
        TutorialViewHolder(parent)

    override fun onBindViewHolder(holder: TutorialViewHolder, position: Int) {
        holder.bind(tutorialList[position])
    }

    override fun getItemCount(): Int = tutorialList.size

    fun setTutorials(list: List<Tutorial>){
        tutorialList.clear()
        tutorialList.addAll(list)
        notifyDataSetChanged()
    }


}

class TutorialViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val titleTextView = itemView.findViewById<TextView>(R.id.tv_tutorial_title)
    private val imageImageView = itemView.findViewById<ImageView>(R.id.iv_tutorial_image)
    private val descriptionTextView = itemView.findViewById<TextView>(R.id.tv_tutorial_description)

    constructor(parent: ViewGroup)
            : this(LayoutInflater.from(parent.context).inflate(R.layout.fragment_tutorial_slide, parent, false))

    fun bind(tutorial: Tutorial) {
        titleTextView.text = tutorial.title
        imageImageView.setImageResource(tutorial.imageRes)
        descriptionTextView.text = tutorial.description
    }
}