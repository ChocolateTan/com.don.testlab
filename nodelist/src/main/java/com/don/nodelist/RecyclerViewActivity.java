package com.don.nodelist;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class RecyclerViewActivity extends AppCompatActivity {

    /** 树中的顶层元素集合 */
    private ArrayList<TreeNode> topNodes;
    /** 所有的元素集合 */
    private ArrayList<TreeNode> allNodes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);


        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        init();

        RecyclerView treeview = (RecyclerView) findViewById(R.id.recycler_view);
        TreeViewRecyclerViewAdapter treeViewAdapter = new TreeViewRecyclerViewAdapter(topNodes, allNodes, inflater);
//        TreeViewItemClickListener treeViewItemClickListener = new TreeViewItemClickListener(treeViewAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        treeview.setLayoutManager(linearLayoutManager);
        treeview.setAdapter(treeViewAdapter);

    }

    private void init() {
        topNodes = new ArrayList<TreeNode>();
        allNodes = new ArrayList<TreeNode>();

        //添加节点  -- 节点名称，节点level，节点id，父节点id，是否有子节点，是否展开

        //添加最外层节点
        TreeNode node1 = new TreeNode("1", TreeNode.TOP_LEVEL, 1, TreeNode.NO_PARENT, true, false);
        TreeNode node2 = new TreeNode("2", TreeNode.TOP_LEVEL, 2, TreeNode.NO_PARENT, false, false);
        TreeNode node3 = new TreeNode("3", TreeNode.TOP_LEVEL, 3, TreeNode.NO_PARENT, false, false);
        TreeNode node4 = new TreeNode("4", TreeNode.TOP_LEVEL, 4, TreeNode.NO_PARENT, false, false);
        TreeNode node5 = new TreeNode("5", TreeNode.TOP_LEVEL, 5, TreeNode.NO_PARENT, false, false);
        TreeNode node6 = new TreeNode("6", TreeNode.TOP_LEVEL, 6, TreeNode.NO_PARENT, false, false);
        TreeNode node7 = new TreeNode("7", TreeNode.TOP_LEVEL, 7, TreeNode.NO_PARENT, false, false);
        TreeNode node8 = new TreeNode("8", TreeNode.TOP_LEVEL, 8, TreeNode.NO_PARENT, false, false);
        TreeNode node9 = new TreeNode("9", TreeNode.TOP_LEVEL, 9, TreeNode.NO_PARENT, false, false);
        TreeNode node10 = new TreeNode("10", TreeNode.TOP_LEVEL, 10, TreeNode.NO_PARENT, false, false);
        TreeNode node11 = new TreeNode("11", TreeNode.TOP_LEVEL, 11, TreeNode.NO_PARENT, false, false);
        TreeNode node12 = new TreeNode("12", TreeNode.TOP_LEVEL, 12, TreeNode.NO_PARENT, false, false);
        TreeNode node13 = new TreeNode("13", TreeNode.TOP_LEVEL, 13, TreeNode.NO_PARENT, false, false);
        TreeNode node14 = new TreeNode("14", TreeNode.TOP_LEVEL, 14, TreeNode.NO_PARENT, false, false);
        TreeNode node15 = new TreeNode("15", TreeNode.TOP_LEVEL, 15, TreeNode.NO_PARENT, false, false);
        TreeNode node16 = new TreeNode("16", TreeNode.TOP_LEVEL, 16, TreeNode.NO_PARENT, false, false);

        topNodes.add(node1);
        topNodes.add(node2);
        topNodes.add(node3);
        topNodes.add(node4);
        topNodes.add(node5);
        topNodes.add(node6);
        topNodes.add(node7);
        topNodes.add(node8);
        topNodes.add(node9);
        topNodes.add(node10);
        topNodes.add(node11);
        topNodes.add(node12);
        topNodes.add(node13);
        topNodes.add(node14);
        topNodes.add(node15);
        topNodes.add(node16);

        allNodes.add(node1);
        allNodes.add(node2);
        allNodes.add(node3);
        allNodes.add(node4);
        allNodes.add(node5);
        allNodes.add(node6);
        allNodes.add(node7);
        allNodes.add(node8);
        allNodes.add(node9);
        allNodes.add(node10);
        allNodes.add(node11);
        allNodes.add(node12);
        allNodes.add(node13);
        allNodes.add(node14);
        allNodes.add(node15);
        allNodes.add(node16);

        TreeNode node1_1 = new TreeNode("1.1", TreeNode.TOP_LEVEL + 1, 101, node1.getId(), true, false);
        TreeNode node1_2 = new TreeNode("1.2", TreeNode.TOP_LEVEL + 1, 102, node1.getId(), false, false);
        TreeNode node1_3 = new TreeNode("1.3", TreeNode.TOP_LEVEL + 1, 103, node1.getId(), false, false);
        TreeNode node1_4 = new TreeNode("1.4", TreeNode.TOP_LEVEL + 1, 104, node1.getId(), false, false);
        TreeNode node1_5 = new TreeNode("1.5", TreeNode.TOP_LEVEL + 1, 105, node1.getId(), false, false);

        TreeNode node1_1_1 = new TreeNode("1.1.1", TreeNode.TOP_LEVEL + 2, 10101, node1_1.getId(), true, false);
        TreeNode node1_1_2 = new TreeNode("1.1.2", TreeNode.TOP_LEVEL + 2, 10102, node1_1.getId(), false, false);
        TreeNode node1_1_3 = new TreeNode("1.1.3", TreeNode.TOP_LEVEL + 2, 10103, node1_1.getId(), false, false);

        TreeNode node1_1_1_1 = new TreeNode("1.1.1.1", TreeNode.TOP_LEVEL + 3, 1010101, node1_1_1.getId(), true, false);
        TreeNode node1_1_1_1_1 = new TreeNode("1.1.1.1.1", TreeNode.TOP_LEVEL + 4, 101010101, node1_1_1_1.getId(), false, false);
        TreeNode node1_1_1_1_2 = new TreeNode("1.1.1.1.2", TreeNode.TOP_LEVEL + 4, 101010102, node1_1_1_1.getId(), false, false);

        allNodes.add(node1_1);
        allNodes.add(node1_2);
        allNodes.add(node1_3);
        allNodes.add(node1_4);
        allNodes.add(node1_5);
        allNodes.add(node1_1_1);
        allNodes.add(node1_1_2);
        allNodes.add(node1_1_3);
        allNodes.add(node1_1_1_1);
        allNodes.add(node1_1_1_1_1);
        allNodes.add(node1_1_1_1_2);
    }
}
