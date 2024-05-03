package com.example.theyellowriver.gearwenxin.annotations;//package com.gearwenxin.annotations;
//
//import jakarta.validation.ConstraintValidator;
//import jakarta.validation.ConstraintValidatorContext;
//
///**
// * @author Ge Mingjia
// * @date 2023/7/20
// */
//public class BetweenValidator implements ConstraintValidator<Between, Double> {
//
//    private double min;
//    private double max;
//    private boolean includeMin;
//    private boolean includeMax;
//
//    @Override
//    public void initialize(Between constraintAnnotation) {
//        this.min = constraintAnnotation.min();
//        this.max = constraintAnnotation.max();
//        this.includeMin = constraintAnnotation.includeMin();
//        this.includeMax = constraintAnnotation.includeMax();
//    }
//
//    @Override
//    public boolean isValid(Double value, ConstraintValidatorContext context) {
//        if (value == null) {
//            return true;
//        }
//
//        if (includeMin && value < min) {
//            return false;
//        }
//
//        if (!includeMin && value <= min) {
//            return false;
//        }
//
//        if (includeMax && value > max) {
//            return false;
//        }
//
//        return !(!includeMax && value >= max);
//    }
//}
