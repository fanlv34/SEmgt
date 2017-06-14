(function () {
    'use strict';

    var module = angular.module('ae-datetimepicker', []);

    module.directive('datetimepicker', [
        '$timeout',
        function ($timeout) {
            return {
                restrict: 'EA',
                require: 'ngModel',
                scope: {
                    options: '=?',
                    onChange: '&?',
                    onClick: '&?'
                },
                link: function ($scope, $element, $attrs, ngModel) {
                    var dpElement = $element.parent().hasClass('input-group') ? $element.parent() : $element;
                    //2016-11-27 momentValue is moment obj. viewValue is string obj.
                    var momentValue = ngModel.$viewValue;

                    $scope.$watch('options', function (newValue) {
                        var dtp = dpElement.data('DateTimePicker');
                        $.map(newValue, function (value, key) {
                            dtp[key](value);
                        });
                        // 2016-11-27 format attr changed. refresh viewValue and momentValue
                        ngModel.$setViewValue(momentValue===null?null:momentValue.format($scope.options.format));
                        momentValue = moment(momentValue===null?null:momentValue.format($scope.options.format));
                    }, true);
                    
                    ngModel.$render = function () {
                        if (!ngModel.$viewValue) {
                            ngModel.$setViewValue(null);
                            momentValue = null;
                        } else if (!moment.isMoment(ngModel.$viewValue)) {
                        	//2016-11-27 fill viewValue with viewValue
//                            ngModel.$setViewValue(moment(ngModel.$viewValue));
                            ngModel.$setViewValue(ngModel.$viewValue);
                            momentValue = moment(ngModel.$viewValue);
                        }
                        dpElement.data('DateTimePicker').date(momentValue);
                    };

                    var isDateEqual = function (d1, d2) {
                        return moment.isMoment(d1) && moment.isMoment(d2) && d1.valueOf() === d2.valueOf();
                    };

                    dpElement.on('dp.change', function (e) {
                        if (!isDateEqual(e.date, momentValue)) {
                            var newValue = e.date === false ? null : e.date;
                            //2016-11-27 fix format newValue 
//                            ngModel.$setViewValue(newValue);
                            ngModel.$setViewValue(newValue === null?null : newValue.format($scope.options.format));

                            $timeout(function () {
                                if (typeof $scope.onChange === 'function') {
                                    $scope.onChange();
                                }
                            });
                        }
                    });


                    dpElement.on('click', function () {
                        $timeout(function () {
                            if (typeof $scope.onClick === 'function') {
                                $scope.onClick();
                            }
                        });
                    });

                    dpElement.datetimepicker($scope.options);
                }
            };
        }
    ]);
})();