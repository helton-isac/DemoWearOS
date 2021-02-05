import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hitg.demowearos.MenuItem
import com.hitg.demowearos.R

class MainMenuAdapter(
    private val context: Context,
    private val data: ArrayList<MenuItem>,
    private val callback: AdapterCallback?
) :
    RecyclerView.Adapter<MainMenuAdapter.RecyclerViewHolder>() {

    interface AdapterCallback {
        fun onItemClicked(menuPosition: Int?)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.main_menu_item, parent, false)
        return RecyclerViewHolder(view)
    }

    class RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var menuContainer: LinearLayout = view.findViewById(R.id.menu_container)
        var menuItem: TextView = view.findViewById(R.id.menu_item)
    }

    override fun onBindViewHolder(
        holder: RecyclerViewHolder,
        position: Int
    ) {
        holder.menuItem.text = data[position].text
        holder.menuContainer.setOnClickListener { callback?.onItemClicked(position) }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}