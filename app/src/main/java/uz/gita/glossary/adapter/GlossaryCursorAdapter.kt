package uz.gita.glossary.adapter

import android.annotation.SuppressLint
import android.database.Cursor
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uz.gita.glossary.R
import uz.gita.glossary.data.model.GlossaryData
import uz.gita.glossary.utils.color

class GlossaryCursorAdapter(
    var cursor: Cursor,
    var query: String
) : RecyclerView.Adapter<GlossaryCursorAdapter.CursorViewHolder>() {

    private var clickItemListener: ((GlossaryData) -> Unit)? = null
    fun setClickItemListener(f: (GlossaryData) -> Unit) {
        clickItemListener = f
    }

    private var clickFavouriteListener: ((GlossaryData, Int) -> Unit)? = null
    fun setClickFavouriteListener(f: (GlossaryData, Int) -> Unit) {
        clickFavouriteListener = f
    }
    inner class CursorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var remember = itemView.findViewById<ImageView>(R.id.remember)
        private var title = itemView.findViewById<TextView>(R.id.title)
        private var subTitle = itemView.findViewById<TextView>(R.id.subTitle)

        init {
            itemView.setOnClickListener {
                clickItemListener?.invoke(getDictionaryDataByPos(adapterPosition))
            }
            remember.setOnClickListener {
                val data = getDictionaryDataByPos(adapterPosition)
                clickFavouriteListener?.invoke(data, adapterPosition)
            }
        }

        fun bind(data: GlossaryData) {
            val spanSt = SpannableString(data.word)
            val foregroundColorSpan = ForegroundColorSpan(color(R.color.purple_700))
            val startIndex = data.word.indexOf(query, 0, true)
            val endIndex = startIndex + query.length
            spanSt.setSpan(
                foregroundColorSpan,
                startIndex,
                endIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            title.text = spanSt
            subTitle.text = data.definition

            if (data.isFavourite == 1)
                remember.setImageResource(R.drawable.ic_bookmark_select)
            else remember.setImageResource(R.drawable.ic_bookmark)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CursorViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_glossary, parent, false)
        return CursorViewHolder(view)
    }

    override fun onBindViewHolder(holder: CursorViewHolder, position: Int) {
        holder.bind(getDictionaryDataByPos(position))
    }

    @SuppressLint("Range")
    private fun getDictionaryDataByPos(pos: Int): GlossaryData {
        cursor.moveToPosition(pos)
        return GlossaryData(
            cursor.getInt(cursor.getColumnIndex("id")),
            cursor.getString(cursor.getColumnIndex("word")),
            cursor.getString(cursor.getColumnIndex("wordtype")),
            cursor.getString(cursor.getColumnIndex("definition")),
            cursor.getInt(cursor.getColumnIndex("isRemember")),
        )
    }

    override fun getItemCount(): Int = cursor.count
}