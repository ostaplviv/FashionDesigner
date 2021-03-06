package ua.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.model.filter.ClothingModelFilter;
import ua.model.view.ClothingModelView;
import ua.service.ClothingModelService;

@Controller
@RequestMapping("/hauteCouture")
public class HauteCoutureController {

    private final ClothingModelService clothingModelService;

    @Autowired
    public HauteCoutureController(ClothingModelService clothingModelService) {
        this.clothingModelService = clothingModelService;
    }

    @ModelAttribute("clothingModelFilter")
    public ClothingModelFilter getFilter() {
        return new ClothingModelFilter();
    }

    @GetMapping
    public String showPage(Model model, @PageableDefault Pageable pageable,
                           @ModelAttribute("clothingModelFilter") ClothingModelFilter clothingModelFilter) {
        // Show only 'Haute Couture' clothes
        clothingModelFilter.setSectionOfClothesName(Collections.singletonList("Haute Couture"));

        Page<ClothingModelView> clothingModelViewsPage = clothingModelService
                .findAllClothingModelViews(clothingModelFilter, pageable);
        model.addAttribute("showClothingModels", clothingModelViewsPage);

        List<ClothingModelView> clothingModelViewsList = clothingModelViewsPage.getContent();
        for (ClothingModelView clothingModelView : clothingModelViewsList) {
            clothingModelView.setPhotoUrls(clothingModelService.findPhotoUrls(clothingModelView.getId()));
        }

        return "hauteCouture";
    }

}
