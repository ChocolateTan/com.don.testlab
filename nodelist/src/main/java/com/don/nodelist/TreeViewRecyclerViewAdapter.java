package com.don.nodelist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by don on 16/05/16.
 */
public class TreeViewRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    /**
     * 所有的数据集合
     */
    private ArrayList<TreeNode> allNodes;
    /**
     * 顶层元素结合
     */
    private ArrayList<TreeNode> topNodes;
    /**
     * LayoutInflater
     */
    private LayoutInflater inflater;
    /**
     * item的行首缩进基数
     */
    private int indentionBase;

    public TreeViewRecyclerViewAdapter(ArrayList<TreeNode> topNodes, ArrayList<TreeNode> allNodes, LayoutInflater inflater) {
        this.topNodes = topNodes;
        this.allNodes = allNodes;
        this.inflater = inflater;
        indentionBase = 100;
    }

    public ArrayList<TreeNode> getTopNodes() {
        return topNodes;
    }

    public ArrayList<TreeNode> getAllNodes() {
        return allNodes;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.tree_item, null);

        return new ViewHolderRecycler(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        ViewHolderRecycler holderRecycler = (ViewHolderRecycler) holder;

        TreeNode element = topNodes.get(position);
        int level = element.getLevel();
        holderRecycler.homeImg.setPadding(
                indentionBase * (level),
                holderRecycler.homeImg.getPaddingTop(),
                holderRecycler.homeImg.getPaddingRight(),
                holderRecycler.homeImg.getPaddingBottom());
        holderRecycler.treeText.setText(element.getContentText());
        if (element.isHasChildren() && !element.isExpanded()) {
            holderRecycler.homeImg.setImageResource(R.mipmap.arrow_right);
            //这里要主动设置一下icon可见，因为convertView有可能是重用了"设置了不可见"的view，下同。
            holderRecycler.homeImg.setVisibility(View.VISIBLE);
        } else if (element.isHasChildren() && element.isExpanded()) {
            holderRecycler.homeImg.setImageResource(R.mipmap.arrow_down);
            holderRecycler.homeImg.setVisibility(View.VISIBLE);
        } else if (!element.isHasChildren()) {
            holderRecycler.homeImg.setImageResource(R.mipmap.arrow_down);
            holderRecycler.homeImg.setVisibility(View.INVISIBLE);
        }


        holderRecycler.rlyItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击的item代表的元素
                TreeNode treeNode = (TreeNode) getItem(position);
                //树中顶层的元素
                ArrayList<TreeNode> topNodes = getTopNodes();
                //元素的数据源
                ArrayList<TreeNode> allNodes = getAllNodes();

                //点击没有子项的item直接返回
                if (!treeNode.isHasChildren()) {
                    return;
                }

                if (treeNode.isExpanded()) {
                    treeNode.setExpanded(false);
                    //删除节点内部对应子节点数据，包括子节点的子节点...
                    ArrayList<TreeNode> elementsToDel = new ArrayList<TreeNode>();
                    for (int i = position + 1; i < topNodes.size(); i++) {
                        if (treeNode.getLevel() >= topNodes.get(i).getLevel())
                            break;
                        elementsToDel.add(topNodes.get(i));
                    }
                    topNodes.removeAll(elementsToDel);
                    notifyDataSetChanged();
                } else {
                    treeNode.setExpanded(true);
                    //从数据源中提取子节点数据添加进树，注意这里只是添加了下一级子节点，为了简化逻辑
                    int i = 1;//注意这里的计数器放在for外面才能保证计数有效
                    for (TreeNode e : allNodes) {
                        if (e.getParendId() == treeNode.getId()) {
                            e.setExpanded(false);
                            topNodes.add(position + i, e);
                            i++;
                        }
                    }
                    notifyDataSetChanged();
                }
            }
        });
    }

    public TreeNode getItem(int position) {
        return topNodes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return topNodes.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return topNodes.size();
    }

    static class ViewHolderRecycler extends RecyclerView.ViewHolder {
        private final RelativeLayout rlyItem;
        private final ImageView homeImg;
        private final TextView treeText;

        public ViewHolderRecycler(View itemView) {
            super(itemView);
            homeImg = (ImageView) itemView.findViewById(R.id.homeImg);
            treeText = (TextView) itemView.findViewById(R.id.treeText);
            rlyItem = (RelativeLayout) itemView.findViewById(R.id.rly_item);
        }
    }
}
