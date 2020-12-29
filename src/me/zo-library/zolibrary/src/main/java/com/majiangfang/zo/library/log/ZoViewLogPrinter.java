package com.majiangfang.zo.library.log;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.majiangfang.zo.library.R;

import java.util.ArrayList;
import java.util.List;

/**
 * ZoViewLogPrinter 将log显示在界面上
 */
public class ZoViewLogPrinter implements ZoLogPrinter{
    private RecyclerView recyclerView;
    private LogAdapter adapter;
    private ZoViewLogPrinterProvider viewProvider;

    public ZoViewLogPrinter(Activity activity) {
        FrameLayout rootView = activity.findViewById(android.R.id.content);
        recyclerView = new RecyclerView(activity);
        adapter = new LogAdapter(LayoutInflater.from(recyclerView.getContext()));
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        viewProvider = new ZoViewLogPrinterProvider(rootView, recyclerView);
    }

    /**
     * 获取ViewProvider，通过ViewProvider可以控制log视图的展示和隐藏
     *
     * @return ViewProvider
     */
    @NonNull
    public ZoViewLogPrinterProvider getViewProvider() {
        return viewProvider;
    }

    @Override
    public void print(@NonNull ZoLogConfig config, int level, String tag, @NonNull String printString) {

        // 将log展示添加到recycleView
        adapter.addItem(new ZoLogMo(System.currentTimeMillis(), level, tag, printString));

        // 滚动到对应的位置
        recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
    }

    private static class LogAdapter extends RecyclerView.Adapter<LogViewHolder> {

        private LayoutInflater inflater;

        private List<ZoLogMo> logs = new ArrayList<>();

        LogAdapter(LayoutInflater inflater) {
            this.inflater = inflater;
        }

        void addItem(ZoLogMo logItem) {
            logs.add(logItem);
            notifyItemInserted(logs.size() - 1);
        }

        @NonNull
        @Override
        public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = inflater.inflate(R.layout.zolog_item, parent, false);
            return new LogViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(LogViewHolder holder, int position) {
            ZoLogMo logItem = logs.get(position);

            int color = getHighlightColor(logItem.level);
            holder.tagView.setTextColor(color);
            holder.messageView.setTextColor(color);

            holder.tagView.setText(logItem.getFlattened());
            holder.messageView.setText(logItem.log);
        }

        /**
         * 跟进log级别获取不同的高了颜色
         *
         * @param logLevel log 级别
         * @return 高亮的颜色
         */
        private int getHighlightColor(int logLevel) {
            int highlight;
            switch (logLevel) {
                case ZoLogType.V:
                    highlight = 0xffbbbbbb;
                    break;
                case ZoLogType.D:
                    highlight = 0xffffffff;
                    break;
                case ZoLogType.I:
                    highlight = 0xff6a8759;
                    break;
                case ZoLogType.W:
                    highlight = 0xffbbb529;
                    break;
                case ZoLogType.E:
                    highlight = 0xffff6b68;
                    break;
                default:
                    highlight = 0xffffff00;
                    break;
            }
            return highlight;
        }

        @Override
        public int getItemCount() {
            return logs.size();
        }
    }


    private static class LogViewHolder extends RecyclerView.ViewHolder {

        TextView tagView;
        TextView messageView;

        LogViewHolder(View itemView) {
            super(itemView);
            tagView = itemView.findViewById(R.id.tag);
            messageView = itemView.findViewById(R.id.message);
        }
    }
}
