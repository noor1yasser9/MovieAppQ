package com.nurbk.ps.movieappq.ui.fragment

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import androidx.core.animation.doOnEnd
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nurbk.ps.movieappq.R
import com.nurbk.ps.movieappq.databinding.FragmentSplashBinding
import com.nurbk.ps.movieappq.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {

    @Inject
    lateinit var viewModel: SplashViewModel

    private lateinit var mBinding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentSplashBinding.inflate(layoutInflater, container, false).apply {
            executePendingBindings()
        }
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.liveData.observe(viewLifecycleOwner) {
            when (it) {
                is SplashViewModel.SplashState.SplashFragment -> {
                    findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
                }
            }

        }
        val b = AnimationUtils.loadAnimation(requireContext(), R.anim.splash)
        b.reset()
        mBinding.apply {
            motionLayout.clearAnimation()
            motionLayout.startAnimation(b)
        }

    }
}