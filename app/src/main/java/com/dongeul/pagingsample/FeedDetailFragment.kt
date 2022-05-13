package com.dongeul.pagingsample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.dongeul.pagingsample.data.Comment
import com.dongeul.pagingsample.data.CommentAdapter
import com.dongeul.pagingsample.data.SampleModel
import com.dongeul.pagingsample.databinding.FragmentFeedDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FeedDetailFragment : Fragment() {
    private lateinit var data: SampleModel.Data
    private var _binding: FragmentFeedDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PagingViewModel by activityViewModels()
    private val args by navArgs<FeedDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFeedDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            with(viewModel) {
                vm = this
                data = args.data as SampleModel.Data
                viewModel.feedEntityLiveData.value = data
                includeLayout.commentListView.adapter = commentAdapter
                updateComment()
            }


            btnSend.setOnClickListener {
                viewModel.updateComment(
                    Comment(
                        commentUser = "나",
                        comment = edtMessage.text.toString()
                    )
                )
                edtMessage.text.clear()
            }
            includeLayout.ivHeart.setOnClickListener {
                viewModel.updateLike()
            }
        }

    }
}