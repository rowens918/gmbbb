package com.example.android.gmsbigblackbox;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.gmsbigblackbox.database.NpcCardEntry;
import java.util.List;


public class NpcAdapter extends RecyclerView.Adapter<NpcAdapter.NpcViewHolder> {

    final private ItemClickListener mItemClickListener;
    public static List<NpcCardEntry> mNpcEntries;
    private Context mContext;



    public NpcAdapter(Context context, ItemClickListener listener) {
        mContext = context;
        mItemClickListener = listener;
    }

    @Override
    public NpcViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the Npc_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.npccard_list_content, parent, false);

        return new NpcViewHolder(view);
    }


    @Override
    public void onBindViewHolder(NpcViewHolder holder, int position) {
        NpcCardEntry NpcEntry = mNpcEntries.get(position);
        String name = NpcEntry.getNpcName();
        String role = NpcEntry.getNpcRole();
        String game = NpcEntry.getNpcGame();

        holder.NpcName.setText(name);
        holder.NpcGame.setText(game);
        holder.NpcRole.setText(role);
    }


    @Override
    public int getItemCount() {
        if (mNpcEntries == null) {
            return 0;
        }
        return mNpcEntries.size();
    }

    public static List<NpcCardEntry> getNpcs() {
        return mNpcEntries;
    }

    public void setNpcs(List<NpcCardEntry> NpcEntries) {
        mNpcEntries = NpcEntries;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }

    // Inner class for creating ViewHolders
    class NpcViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView NpcName;
        TextView NpcRole;
        TextView NpcGame;

        public NpcViewHolder(View itemView) {
            super(itemView);

            NpcName = itemView.findViewById(R.id.tv_list_name);
            NpcRole = itemView.findViewById(R.id.tv_list_role);
            NpcGame = itemView.findViewById(R.id.tv_list_game);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int elementId = mNpcEntries.get(getAdapterPosition()).getId();
            mItemClickListener.onItemClickListener(elementId);
        }
    }
}