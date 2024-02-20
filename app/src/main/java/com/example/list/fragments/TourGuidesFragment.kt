package com.example.list.fragments

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.list.MainActivity
import com.example.list.data.TourGuides
import com.example.list.interfaces.MainActivityInterface
import com.example.list1110.R
import com.example.list1110.databinding.FragmentTourguideBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class TourGuidesFragment : Fragment(), MainActivity.Edit {

    companion object {
        private var INSTANCE : TourGuidesFragment? = null
        fun getInstance(): TourGuidesFragment{
            if (INSTANCE == null) INSTANCE = TourGuidesFragment()
            return INSTANCE ?: throw Exception("TourGuideFragment не создан")
        }
        fun newInstance(): TourGuidesFragment{
            INSTANCE = TourGuidesFragment()
            return INSTANCE!!
        }
    }

    private lateinit var viewModel: TourGuidesViewModel
    private var tabPosition : Int = 0
    private lateinit var _binding: FragmentTourguideBinding
    private val binding get() = _binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTourguideBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(TourGuidesViewModel::class.java)
        val ma = (requireActivity() as MainActivityInterface)
        ma.updateTitle("Туристическое агенства \"${viewModel.company?.name}\"")

        viewModel.tourGuidesList.observe(viewLifecycleOwner){
            createUI(it)
        }
    }

    private inner class GroipPageAdapter(fa: FragmentActivity, private val makers: List<TourGuides>?): FragmentStateAdapter(fa){
        override fun getItemCount(): Int {
            return (makers?.size?: 0)
        }

        override fun createFragment(position: Int): Fragment {
            return OrderedFragment.newInstance(makers!![position])
        }
    }

    private fun createUI(tourGuidesList: List<TourGuides>){
        binding.tlTourGuide.clearOnTabSelectedListeners()
        binding.tlTourGuide.removeAllTabs()

        for (i in 0 until (tourGuidesList.size)){
            binding.tlTourGuide.addTab(binding.tlTourGuide.newTab().apply {
                text = tourGuidesList.get(i).name
            })
        }
        val adapter = GroipPageAdapter(requireActivity(), tourGuidesList)
        binding.vpTourGuide.adapter=adapter
        TabLayoutMediator(binding.tlTourGuide, binding.vpTourGuide, true, true){
                tab, pos ->
            tab.text = tourGuidesList.get(pos).name
        }.attach()
        tabPosition = 0
        if (viewModel.group != null)
            tabPosition = if (viewModel.getCurrentListPosition>=0)
                viewModel.getCurrentListPosition
            else
                0
        viewModel.setCurrentGroup(tabPosition)
        binding.tlTourGuide.selectTab(binding.tlTourGuide.getTabAt(tabPosition), true)

        binding.tlTourGuide.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tabPosition = tab?.position!!
                viewModel.setCurrentGroup(tourGuidesList[tabPosition])
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    override fun append() {
        editGroup()
    }

    override fun update() {
        editGroup(viewModel.group?.name?: "")
    }

    override fun delete(){
        deleteDialog()
    }

    private fun deleteDialog(){
        if (viewModel.group == null) return
        AlertDialog.Builder(requireContext())
            .setTitle("Удаление")
            .setMessage("Вы действительно хотите удалить гида ${viewModel.group?.name?:""}?")
            .setPositiveButton("ДА"){ _, _ ->
                viewModel.deleteGroup()
            }
            .setNegativeButton("НЕТ", null)
            .setCancelable(true)
            .create()
            .show()
    }

    private fun editGroup(groupName: String = ""){
        val mDialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_input_name, null)
        val messageText = mDialogView.findViewById<TextView>(R.id.tvInfo)
        val inputString = mDialogView.findViewById<TextView>(R.id.etInput)
        inputString.setText(groupName)
        messageText.text = "Укажите имя гида"

        android.app.AlertDialog.Builder(requireContext())
            .setTitle("ИЗМЕНЕНИЕ ДАННЫХ")
            .setView(mDialogView)
            .setPositiveButton("Подтверждаю"){ _, _ ->
                if (inputString.text.isNotBlank()){
                    if (groupName.isBlank())
                        viewModel.appendGroup(inputString.text.toString())
                    else
                        viewModel.updateGroup(inputString.text.toString())
                }

            }
            .setNegativeButton("Отмена", null)
            .setCancelable(true)
            .create()
            .show()

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TourGuidesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}