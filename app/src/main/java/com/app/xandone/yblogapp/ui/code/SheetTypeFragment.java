package com.app.xandone.yblogapp.ui.code;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.xandone.yblogapp.R;
import com.app.xandone.yblogapp.config.AppConfig;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: Admin
 * created on: 2020/9/7 15:41
 * description:
 */
public class SheetTypeFragment extends BottomSheetDialogFragment {
    @BindView(R.id.type_recycler)
    RecyclerView recyclerView;

    private BaseQuickAdapter<String, BaseViewHolder> mAdapter;
    private List<String> types;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_sheet_type, container, false);
        int height = (int) (AppConfig.SCREEN_HEIGHT * 0.8);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        view.setLayoutParams(layoutParams);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        types = Arrays.asList(getResources().getStringArray(R.array.code_type_list));
        initItemTouchHelper();
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_code_type, types) {
            @Override
            protected void convert(@NonNull BaseViewHolder baseViewHolder, String s) {
                baseViewHolder.setText(R.id.type_tv, s);
                baseViewHolder.getView(R.id.type_tv).setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        mItemHelper.startDrag(baseViewHolder);
                        return false;
                    }
                });
            }
        };

        recyclerView.setAdapter(mAdapter);
    }

    private ItemTouchHelper mItemHelper;

    private void initItemTouchHelper() {
        mItemHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                    final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                    final int swipeFlags = 0;
                    return makeMovementFlags(dragFlags, swipeFlags);
                } else {
                    final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                    final int swipeFlags = 0;
                    return makeMovementFlags(dragFlags, swipeFlags);
                }
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //得到当拖拽的viewHolder的Position
                int fromPosition = viewHolder.getAdapterPosition();
                //拿到当前拖拽到的item的viewHolder
                int toPosition = target.getAdapterPosition();
                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(types, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(types, i, i - 1);
                    }
                }
                mAdapter.notifyItemMoved(fromPosition, toPosition);
                return true;
            }

            //拖动完成
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//                Toast.makeText(MainActivity.this, "拖拽完成 方向" + direction, Toast.LENGTH_SHORT).show();
            }

            //选中
            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                    viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
                }
            }

            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                viewHolder.itemView.setBackgroundColor(0);

            }

            //重写拖拽不可用
            @Override
            public boolean isLongPressDragEnabled() {
                return false;
            }

        });

        mItemHelper.attachToRecyclerView(recyclerView);
    }
}
