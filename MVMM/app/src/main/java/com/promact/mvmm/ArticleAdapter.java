package com.promact.mvmm;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.promact.mvmm.databinding.ArticleItemBinding;

import java.util.List;

/**
 * Created by grishma on 07-11-2017.
 */

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.BindingHolder>  {
    private List<Article> mArticles;
    private Context mContext;

    public ArticleAdapter(List<Article> mArticles, Context mContext) {
        this.mArticles = mArticles;
        this.mContext = mContext;
    }
    @Override
    public ArticleAdapter.BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ArticleItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.article_item, parent, false);

        return new BindingHolder(binding);
    }

    @Override
    public void onBindViewHolder(ArticleAdapter.BindingHolder holder, int position) {
        ArticleItemBinding itemBinding =holder.binding;
        itemBinding.setAvm(new ArticleViewModel(mArticles.get(position),mContext));
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }
    public static class BindingHolder extends RecyclerView.ViewHolder {
        private ArticleItemBinding binding;

        public BindingHolder(ArticleItemBinding binding) {
            super(binding.contactCard);
            this.binding = binding;
        }
    }
}
