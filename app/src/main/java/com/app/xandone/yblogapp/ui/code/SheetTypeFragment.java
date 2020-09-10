package com.app.xandone.yblogapp.ui.code;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.app.xandone.baselib.cache.SpHelper;
import com.app.xandone.baselib.event.SimplEvent;
import com.app.xandone.baselib.utils.JsonUtils;
import com.app.xandone.widgetlib.utils.SpacesItemDecoration;
import com.app.xandone.yblogapp.App;
import com.app.xandone.yblogapp.R;
import com.app.xandone.yblogapp.config.AppConfig;
import com.app.xandone.yblogapp.constant.IConstantKey;
import com.app.xandone.yblogapp.constant.ISpKey;
import com.app.xandone.yblogapp.model.bean.CodeTypeBean;
import com.app.xandone.yblogapp.model.event.CodeTypeEvent;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author: Admin
 * created on: 2020/9/7 15:41
 * description:
 */
public class SheetTypeFragment extends BottomSheetDialogFragment {
    @BindView(R.id.type_recycler)
    RecyclerView recycler;
    @BindView(R.id.type_remove_recycler)
    RecyclerView typeRemovRecycler;
    @BindView(R.id.edit_tv)
    TextView editTv;


    private BaseQuickAdapter<CodeTypeBean, BaseViewHolder> mAdapter;
    private BaseQuickAdapter<CodeTypeBean, BaseViewHolder> mRemoveAdapter;
    private List<CodeTypeBean> types;
    private List<CodeTypeBean> removeTypes;
    private ItemTouchHelper mItemHelper;

    //是否为编辑状态
    private boolean isEditState = false;

    public static SheetTypeFragment getInstance(ArrayList<CodeTypeBean> codeTypeBeans, ArrayList<CodeTypeBean> removeBeans) {
        SheetTypeFragment fragment = new SheetTypeFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(IConstantKey.DATA, codeTypeBeans);
        bundle.putParcelableArrayList(IConstantKey.DATA2, removeBeans);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

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
        if (getArguments() == null) {
            return;
        }
        types = new ArrayList<>(getArguments().getParcelableArrayList(IConstantKey.DATA));
        removeTypes = new ArrayList<>(getArguments().getParcelableArrayList(IConstantKey.DATA2));
        initItemTouchHelper();
        recycler.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recycler.addItemDecoration(new SpacesItemDecoration(App.sContext, 10, 10, 10));
        mAdapter = new BaseQuickAdapter<CodeTypeBean, BaseViewHolder>(R.layout.item_code_type, types) {
            @Override
            protected void convert(@NonNull BaseViewHolder baseViewHolder, CodeTypeBean bean) {
                baseViewHolder.setText(R.id.type_tv, bean.getTypeName());
                baseViewHolder.setGone(R.id.type_del_iv, !isEditState);
                if (isEditState) {
                    loadShakeAnim(baseViewHolder.itemView);
                } else {
                    cancelShakeAnim(baseViewHolder.itemView);
                }

                baseViewHolder.getView(R.id.type_tv).setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        mItemHelper.startDrag(baseViewHolder);
                        if (!isEditState) {
                            isEditState = true;
                            editTv.setText("完成");
                            mAdapter.notifyDataSetChanged();
                            mRemoveAdapter.notifyDataSetChanged();
                        }
                        return true;
                    }
                });
            }
        };

        mAdapter.addChildClickViewIds(R.id.type_del_iv);
        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                if (view.getId() == R.id.type_del_iv) {
                    removeTypes.add(types.get(position));
                    types.remove(position);
                    mAdapter.notifyItemRemoved(position);
                    mRemoveAdapter.notifyDataSetChanged();
                }
            }
        });

        recycler.setAdapter(mAdapter);

        initRemoveRecycler();
    }

    private void initRemoveRecycler() {
        typeRemovRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        typeRemovRecycler.addItemDecoration(new SpacesItemDecoration(App.sContext, 10, 10, 10));
        mRemoveAdapter = new BaseQuickAdapter<CodeTypeBean, BaseViewHolder>(R.layout.item_remove_code_type, removeTypes) {
            @Override
            protected void convert(@NonNull BaseViewHolder baseViewHolder, CodeTypeBean bean) {
                baseViewHolder.setText(R.id.type_tv, bean.getTypeName());
                baseViewHolder.setGone(R.id.type_del_iv, !isEditState);
            }
        };

        typeRemovRecycler.setAdapter(mRemoveAdapter);

        mRemoveAdapter.addChildClickViewIds(R.id.type_del_iv);
        mRemoveAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                if (view.getId() == R.id.type_del_iv) {
                    types.add(removeTypes.get(position));
                    removeTypes.remove(position);
                    mRemoveAdapter.notifyItemRemoved(position);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * 缓存自定义的"我的频道"
     */
    private void save2Cache() {
        SpHelper.save2DefaultSp(App.sContext, ISpKey.CODE_TYPE, JsonUtils.obj2Json(types));
    }


    @OnClick({R.id.edit_tv})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.edit_tv:
                isEditState = !isEditState;
                editTv.setText(isEditState ? "完成" : "编辑");
                mAdapter.notifyDataSetChanged();
                mRemoveAdapter.notifyDataSetChanged();
                if (!isEditState) {
                    EventBus.getDefault().post(new CodeTypeEvent(types));
                    save2Cache();
                }
                break;
            default:
                break;
        }
    }

    private void loadShakeAnim(View view) {
        Animation animation = AnimationUtils.loadAnimation(App.sContext, R.anim.shake_anim);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);
        view.startAnimation(animation);
    }

    private void cancelShakeAnim(View view) {
        view.clearAnimation();
    }


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

        mItemHelper.attachToRecyclerView(recycler);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageReceived(SimplEvent event) {
    }

}
