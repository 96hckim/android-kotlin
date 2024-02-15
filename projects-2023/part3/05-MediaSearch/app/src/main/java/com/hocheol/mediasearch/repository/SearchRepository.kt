package com.hocheol.mediasearch.repository

import com.hocheol.mediasearch.model.ListItem
import io.reactivex.rxjava3.core.Observable

interface SearchRepository {

    fun search(query: String): Observable<List<ListItem>>
}