package com.montwell.showcase.ui.movie_detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.montwell.showcase.R
import com.montwell.showcase.api.dto.VideoDTO

class VideoAdapter(
    context: Context,
    private val resource: Int,
    items: List<VideoDTO>
) : ArrayAdapter<VideoDTO>(context, resource, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            val layoutInflater = LayoutInflater.from(parent.context)
            view = layoutInflater.inflate(resource, null)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val video = getItem(position)

        if (video != null) {
            viewHolder.videoName.text = video.name
            viewHolder.videoType.text = video.type
            viewHolder.videoContainer.setOnClickListener {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(YOU_TUBE_BASE_URL + video.key)
                )
                parent.context.startActivity(intent)
            }
        }

        return view
    }

    class ViewHolder(view: View?) {
        val videoContainer = view?.findViewById(R.id.videoContainer) as ConstraintLayout
        val videoName = view?.findViewById(R.id.videoName) as TextView
        val videoType = view?.findViewById(R.id.videoType) as TextView
    }

    companion object {

        private val LOG_TAG = VideoAdapter::class.java.canonicalName

        private const val YOU_TUBE_BASE_URL = "https://www.youtube.com/watch?v="
    }
}
