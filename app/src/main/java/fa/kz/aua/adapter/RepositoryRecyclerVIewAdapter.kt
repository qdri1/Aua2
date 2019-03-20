package fa.kz.aua.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import fa.kz.aua.entity.Repository
import fa.kz.aua.databinding.RvItemRepositoryBinding

class RepositoryRecyclerViewAdapter(
    private var items: ArrayList<Repository>,
    private var listener: OnItemClickListener
) : RecyclerView.Adapter<RepositoryRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(p0.context)
        val binding = RvItemRepositoryBinding.inflate(layoutInflater, p0, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position], listener)

    override fun getItemCount(): Int = items.size

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun replaceData(items: ArrayList<Repository>) {
        this.items = items
        notifyDataSetChanged()
    }

    class ViewHolder(private var binding: RvItemRepositoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(repo: Repository, listener: OnItemClickListener?) {
            binding.repository = repo
            if (listener != null) {
                binding.root.setOnClickListener { listener.onItemClick(layoutPosition) }
            }
            binding.executePendingBindings()
        }
    }

}