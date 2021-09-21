package com.hocheol.githubrepository

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import com.hocheol.githubrepository.data.entity.GithubRepoEntity
import com.hocheol.githubrepository.databinding.ActivityRepositoryBinding
import com.hocheol.githubrepository.extensions.loadCenterInside
import com.hocheol.githubrepository.utillity.RetrofitUtil
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class RepositoryActivity : AppCompatActivity(), CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var binding: ActivityRepositoryBinding

    companion object {
        const val REPOSITORY_OWNER_KEY = "REPOSITORY_OWNER_KEY"
        const val REPOSITORY_NAME_KEY = "REPOSITORY_NAME_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepositoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repositoryOwner = intent.getStringExtra(REPOSITORY_OWNER_KEY) ?: kotlin.run {
            showToast("Repository Owner 이름이 없습니다.")
            finish()
            return
        }

        val repositoryName = intent.getStringExtra(REPOSITORY_NAME_KEY) ?: kotlin.run {
            showToast("Repository Name 이름이 없습니다.")
            finish()
            return
        }

        showProgressBar(true)

        launch {
            loadRepository(repositoryOwner, repositoryName)?.let {
                setData(it)
            } ?: run {
                showToast("Repository 정보가 없습니다.")
                finish()
            }
        }

    }

    private suspend fun loadRepository(repositoryOwner: String, repositoryName: String): GithubRepoEntity? =
        withContext(coroutineContext) {
            var repositoryEntity: GithubRepoEntity? = null

            withContext(Dispatchers.IO) {
                val response = RetrofitUtil.githubApiService.getRepository(
                    ownerLogin = repositoryOwner,
                    repoName = repositoryName
                )

                if (response.isSuccessful) {
                    val body = response.body()

                    withContext(Dispatchers.Main) {
                        body?.let { repo ->
                            repositoryEntity = repo
                        }
                    }
                }
            }

            repositoryEntity
        }

    private fun setData(githubRepoEntity: GithubRepoEntity) = with(binding) {
        showProgressBar(false)

        ownerProfileImageView.loadCenterInside(githubRepoEntity.owner.avatarUrl, 42f)
        ownerNameAndRepoNameTextView.text = "${githubRepoEntity.owner.login}/${githubRepoEntity.name}"
        stargazersCountText.text = githubRepoEntity.stargazersCount.toString()
        githubRepoEntity.language?.let { language ->
            languageText.isGone = false
            languageText.text = language
        } ?: kotlin.run {
            languageText.isGone = false
            languageText.text = ""
        }
        descriptionTextView.text = githubRepoEntity.description
        updateTimeTextView.text = githubRepoEntity.updatedAt
    }

    private fun showProgressBar(isShown: Boolean) = with(binding) {
        progressBar.isGone = isShown.not()
    }

    private fun showToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

}