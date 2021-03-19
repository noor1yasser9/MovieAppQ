package com.nurbk.ps.movieappq.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nurbk.ps.movieappq.databinding.FragmentSeeAllBinding

class SeeAllFragment : Fragment() {

    private lateinit var mBinding: FragmentSeeAllBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentSeeAllBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


}