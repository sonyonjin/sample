package kr.mediascope.smartsing.demo.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.Dimension
import androidx.recyclerview.widget.RecyclerView
import kr.mediascope.smartsing.demo.R

class StyleAdaptor(context: Context) : RecyclerView.Adapter<StyleAdaptor.ViewHolder>() {
    private val mInflater: LayoutInflater
    private var mClickListener: ItemClickListener? = null
    var selectedPosition = 0
    var bDown = false
    var context: Context
    var styleDatas = emptyList<Data>()

    // data is passed into the constructor
    init {
        mInflater = LayoutInflater.from(context)
        this.context = context
        val style_off = context.resources.obtainTypedArray(R.array.styleOff)
        val data: MutableList<Data> = ArrayList()
        data.add(
            Data(
                R.drawable.btn_sing_style_01_audition_off,
                context.getString(R.string.style_Audition),
                "SingitStyle",
                false
            )
        )
        data.add(
            Data(
                R.drawable.btn_sing_style_02_pure_off,
                context.getString(R.string.style_Pure),
                "SingitStyle",
                false
            )
        )
        data.add(
            Data(
                R.drawable.btn_sing_style_03_stage_off,
                context.getString(R.string.style_Stage),
                "SingitStyle",
                false
            )
        )
        data.add(
            Data(
                R.drawable.btn_sing_style_04_whisper_off,
                context.getString(R.string.style_Whisper),
                "SingitStyle",
                false
            )
        )
        data.add(
            Data(
                R.drawable.btn_sing_style_05_karaoke_off,
                context.getString(R.string.style_Karaoke),
                "SingitStyle",
                false
            )
        )
        data.add(
            Data(
                R.drawable.btn_sing_style_06_studio_off,
                context.getString(R.string.style_Studio),
                "SingitStyle",
                false
            )
        )
        data.add(
            Data(
                R.drawable.btn_sing_style_07_hall_off,
                context.getString(R.string.style_Hallway),
                "SingitStyle",
                false
            )
        )
        data.add(
            Data(
                R.drawable.btn_sing_style_8_busking_off,
                context.getString(R.string.style_Busking),
                "SingitStyle",
                false
            )
        )
        data.add(
            Data(
                R.drawable.btn_sing_style_9_denoising_off,
                context.getString(R.string.style_Denoising),
                "SingitStyle",
                false
            )
        )
        data.add(
            Data(
                R.drawable.btn_sing_style_10_cave_off,
                context.getString(R.string.style_Cave),
                "SingitStyle",
                false
            )
        )
        styleDatas = data
    }

    // inflates the row layout from xml when needed
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val view = mInflater.inflate(R.layout.style_item, parent, false)
        return ViewHolder(view)
    }

    // binds the data to the textview in each row
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (imageId, texture) = styleDatas[position]
        if (selectedPosition == position) {
            val style_on = context.resources.obtainTypedArray(R.array.styleOn)
            holder.imageView.setImageResource(style_on.getResourceId(selectedPosition, -1))
        } else {
            holder.imageView.setImageResource(imageId)
        }
        holder.txtView.setTextSize(Dimension.SP, 10f)
        holder.txtView.text = texture
    }

    // total number of rows
    override fun getItemCount(): Int {
        return styleDatas.size
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        //public TextView myTextView;
        var imageView: ImageView
        var txtView: TextView
        var loadingView: ProgressBar? = null

        init {
            imageView = itemView.findViewById<View>(R.id.imgView) as ImageView
            itemView.setOnClickListener(this)
            txtView = itemView.findViewById<View>(R.id.styleTxt) as TextView
            txtView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            if (mClickListener != null) mClickListener!!.onStyleClick(view, adapterPosition)
            selectedPosition = adapterPosition
            notifyDataSetChanged()
        }
    }

    // convenience method for getting data at click position
    fun getItem(id: Int): Data {
        return styleDatas[id]
    }

    // allows clicks events to be caught
    fun setClickListener(itemClickListener: ItemClickListener?) {
        mClickListener = itemClickListener
    }

    // parent activity will implement this method to respond to click events
    interface ItemClickListener {
        //   void onItemClick(View view, int position) throws RemoteException, IntentSender.SendIntentException;
        fun onStyleClick(view: View, position: Int)
    }
}