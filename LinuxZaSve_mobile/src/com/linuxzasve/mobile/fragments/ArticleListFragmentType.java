package com.linuxzasve.mobile.fragments;

/**
 * MainActivity and ArticleSearchActivity use the same fragment (ArticleListFragment). ArticleListFragmentType is
 * passed to a ArticleListFragment constructor to tell them apart.
 *
 * @author dejan
 */
public enum ArticleListFragmentType {
    LIST, SEARCH
}
