package com.example.foopie.ui.fragments.recipe.bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.foopie.databinding.RecipesBottomSheetNewBinding
import com.example.foopie.util.Constants.Companion.DEFAULT_NUTRIENT_TYPE
import com.example.foopie.util.Constants.Companion.DEFAULT_NUTRIENT_VALUE
import com.example.foopie.viewmodels.RecipesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup


class RecipesBottomSheetNew : BottomSheetDialogFragment() {

    private lateinit var recipesViewModel: RecipesViewModel


    private var _binding: RecipesBottomSheetNewBinding? = null
    private val binding get() = _binding!!

    private var nutrientsTypeChip = DEFAULT_NUTRIENT_TYPE
    private var nutrientsTypeChipId = 0
    private var nutrientsValueChip = DEFAULT_NUTRIENT_VALUE
    private var nutrientsValueChipId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipesViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = RecipesBottomSheetNewBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        recipesViewModel.readNutrientTypeAndValue.asLiveData()
            .observe(viewLifecycleOwner) { value ->
                nutrientsTypeChip = value.selectedNutrientsType
                nutrientsValueChip = value.selectedNutrientsValue
                updateChip(value.selectedNutrientsTypeId, binding.nutrientsTypeChipGroup)
                updateChip(value.selectedNutrientsValueId, binding.nutrientsValueChipGroup)
            }

        binding.nutrientsTypeChipGroup.setOnCheckedChangeListener { group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId)
            val selectedNutrientType = chip.text.toString()
            nutrientsTypeChip = selectedNutrientType
            nutrientsTypeChipId = selectedChipId
        }

        binding.nutrientsValueChipGroup.setOnCheckedChangeListener { group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId)
            val selectedNutrientTypeValue = chip.text.toString()
            nutrientsValueChip = selectedNutrientTypeValue
            nutrientsValueChipId = selectedChipId
        }

        binding.applyButtonNewBottomSheet.setOnClickListener {
            recipesViewModel.saveNutrientTypeAndValueTemp(
                nutrientsTypeChip,
                nutrientsTypeChipId,
                nutrientsValueChip,
                nutrientsValueChipId
            )
            val action =
                RecipesBottomSheetNewDirections.actionRecipesBottomSheetNewToRecipeFragment()
            action.backFromBottomSheet = true
            findNavController().navigate(action)
        }

        return binding.root
    }

    private fun updateChip(chipId: Int, chipGroup: ChipGroup) {
        if (chipId != 0) {
            try {
                val targetView = chipGroup.findViewById<Chip>(chipId)
                targetView.isChecked = true
//                chipGroup.requestChildFocus(targetView, targetView)
            } catch (e: Exception) {
                Log.d("RecipesBottomSheetNew", e.message.toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}