package com.hocheol.delivery.screen.main.home.restaurant.detail

import android.content.ClipDescription.MIMETYPE_TEXT_PLAIN
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.hocheol.delivery.R
import com.hocheol.delivery.data.entity.RestaurantEntity
import com.hocheol.delivery.data.entity.RestaurantFoodEntity
import com.hocheol.delivery.databinding.ActivityRestaurantDetailBinding
import com.hocheol.delivery.extensions.fromDpToPx
import com.hocheol.delivery.extensions.load
import com.hocheol.delivery.screen.base.BaseActivity
import com.hocheol.delivery.screen.main.home.restaurant.RestaurantListFragment
import com.hocheol.delivery.screen.main.home.restaurant.detail.menu.RestaurantMenuListFragment
import com.hocheol.delivery.screen.main.home.restaurant.detail.review.RestaurantReviewListFragment
import com.hocheol.delivery.widget.adapter.RestaurantDetailListFragmentPagerAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import kotlin.math.abs

class RestaurantDetailActivity : BaseActivity<RestaurantDetailViewModel, ActivityRestaurantDetailBinding>() {

    override val viewModel by viewModel<RestaurantDetailViewModel> {
        parametersOf(
            intent.getParcelableExtra<RestaurantEntity>(RestaurantListFragment.RESTAURANT_KEY)
        )
    }

    override fun getViewBinding(): ActivityRestaurantDetailBinding =
        ActivityRestaurantDetailBinding.inflate(layoutInflater)

    private lateinit var viewPagerAdapter: RestaurantDetailListFragmentPagerAdapter

    override fun initViews() {
        initAppBar()
    }

    private fun initAppBar() = with(binding) {
        appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val topPadding = 300f.fromDpToPx().toFloat()
            val realAlphaScrollHeight = appBarLayout.measuredHeight - appBarLayout.totalScrollRange
            val abstractOffset = abs(verticalOffset)

            val realAlphaVerticalOffset: Float =
                if (abstractOffset - topPadding < 0) 0f else abstractOffset - topPadding

            if (abstractOffset < topPadding) {
                restaurantTitleTextView.alpha = 0f
                return@OnOffsetChangedListener
            }

            val percentage = realAlphaVerticalOffset / realAlphaScrollHeight
            restaurantTitleTextView.alpha = 1 - (if (1 - percentage * 2 < 0) 0f else 1 - percentage * 2)
        })

        toolbar.setNavigationOnClickListener { finish() }

        callButton.setOnClickListener {
            viewModel.getRestaurantTelNumber()?.let { telNumber ->
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$telNumber"))
                startActivity(intent)
            }
        }

        likeButton.setOnClickListener {
            viewModel.toggleLikeRestaurant()
        }

        shareButton.setOnClickListener {
            viewModel.getRestaurantInfo()?.let { restaurantInfo ->
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = MIMETYPE_TEXT_PLAIN
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "맛있는 음식점 : ${restaurantInfo.restaurantTitle}" +
                                "\n평점 : ${restaurantInfo.grade}" +
                                "\n연락처 : ${restaurantInfo.restaurantTelNumber}"
                    )
                    Intent.createChooser(this, "친구에게 공유하기")
                }
                startActivity(intent)
            }
        }
    }

    override fun observeData() = viewModel.restaurantDetailStateLiveData.observe(this) {
        when (it) {
            is RestaurantDetailState.Loading -> {
                handleLoading()
            }
            is RestaurantDetailState.Success -> {
                handleSuccess(it)
            }
            else -> Unit
        }
    }

    private fun handleLoading() = with(binding) {
        progressBar.visibility = View.VISIBLE
    }

    private fun handleSuccess(state: RestaurantDetailState.Success) = with(binding) {
        progressBar.visibility = View.GONE

        val restaurantEntity = state.restaurantEntity

        callButton.isGone = restaurantEntity.restaurantTelNumber == null

        restaurantTitleTextView.text = restaurantEntity.restaurantTitle
        restaurantImageView.load(restaurantEntity.restaurantImageUrl)
        restaurantMainTitleTextView.text = restaurantEntity.restaurantTitle
        ratingBar.rating = restaurantEntity.grade
        deliveryTimeText.text =
            getString(
                R.string.delivery_expected_time,
                restaurantEntity.deliveryTimeRange.first,
                restaurantEntity.deliveryTimeRange.second
            )
        deliveryTipText.text =
            getString(
                R.string.delivery_tip_range,
                restaurantEntity.deliveryTipRange.first,
                restaurantEntity.deliveryTipRange.second
            )
        likeText.setCompoundDrawablesWithIntrinsicBounds(
            ContextCompat.getDrawable(
                this@RestaurantDetailActivity, if (state.isLiked == true) {
                    R.drawable.ic_heart_enable
                } else {
                    R.drawable.ic_heart_disable
                }
            ),
            null, null, null
        )

        if (::viewPagerAdapter.isInitialized.not()) {
            initViewPager(state.restaurantEntity.restaurantInfoId, state.restaurantFoodList)
        }
    }

    private fun initViewPager(restaurantInfoId: Long, restaurantFoodList: List<RestaurantFoodEntity>?) {
        viewPagerAdapter = RestaurantDetailListFragmentPagerAdapter(
            this,
            listOf(
                RestaurantMenuListFragment.newInstance(
                    restaurantInfoId,
                    ArrayList(restaurantFoodList ?: listOf())
                ),
                RestaurantReviewListFragment.newInstance(
                    restaurantInfoId
                )
            )
        )
        binding.menuAndReviewViewPager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.menuAndReviewTabLayout, binding.menuAndReviewViewPager) { tab, position ->
            tab.setText(RestaurantDetailCategory.values()[position].categoryNameId)
        }.attach()
    }

    companion object {

        fun newIntent(context: Context, restaurantEntity: RestaurantEntity) =
            Intent(context, RestaurantDetailActivity::class.java).apply {
                putExtra(RestaurantListFragment.RESTAURANT_KEY, restaurantEntity)
            }

    }

}