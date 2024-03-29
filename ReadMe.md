30. Модельная система регулирования домашнего отопления  
    Система отопления представляет собой водный обогреватель, работающий на
    газе и нагревающий воду в батареях отопления, установленных в комнатах дома
    (включая гостиную, рабочий кабинет, кухню, ванную комнату и другие
    помещения). В каждой комнате есть специальный клапан, регулирующий
    поступление горячей воды в батареи этой комнаты, и, тем самым – температуру в
    этой комнате. Возможные положения клапана (полностью
    открыт/закрыт/полуоткрыт) могут быть установлены дистанционно. В каждой
    комнате находятся также датчик текущей температуры и инфракрасный датчик
    присутствия людей, их значения используются для регулирования температуры.
    Основное назначение системы автоматического регулирования отопления
    – поддержание в каждой из комнат дома нужной температуры путем установки
    соответствующих положений клапанов обогревателя. Пользователи системы
    (жильцы дома) могут включать и выключать систему регулирования, а также
    задавать рабочую температуру в каждой комнате, т.е. температуру, которая
    должна быть в комнате в случае присутствия в ней людей. В случае же их
    отсутствия в целях экономии должна поддерживаться температура ожидания –
    она определяется на М градусов (1≤ М ≤ 5) ниже рабочей в этой комнате.
    Системе известно обычное недельное расписание пребывания людей в
    комнатах дома – для того, чтобы заранее, к моменту ожидаемого появления в
    конкретной комнате людей, начать прогревать ее до рабочей температуры.
    Регулирование температуры системой основано на показаниях датчиков
    температуры и присутствия людей, заданных величинах рабочей температуры в
    каждой комнате и недельного графика пребывания людей в доме, а также на
    показаниях таймера, который обеспечивает непрерывный поминутный отсчет
    текущего времени. Если температура в каком-либо помещении опускается или
    поднимается на N градусов (1≤ N ≤ 5) ниже или выше требуемой, то система
    формирует команду на изменение положения клапана в этой комнате. Модельная
    система регулирования отопления подсчитывает также общий расход топлива на
    обогрев (в условных единицах), при условии, что на обогрев 1 кв. м комнаты за 1
    минуту расходуется величина Р=С⋅ K, где значение K соответствует положению
    клапана обогревателя в этой комнате: K=0 – закрыт, K=2 – полуоткрыт, K=5 –
    открыт полностью; а С – некоторая заранее заданная для каждой комнаты
    константа (зависит от площади батарей в этой комнате). Цель моделирования –
    изучение зависимости величины расхода топлива от параметров M и N и
    недельного расписания занятости комнат дома. Для проведения экспериментов
    необходимо программно эмулировать показания датчиков текущей температуры и
    присутствия людей в комнатах. Следует считать, что при закрытом клапане
    обогревателя температура в каждой комнате медленно падает (линейно по
    времени, коэффициент линейной зависимости определяется временем суток), при
    открытом – растет (по аналогичному закону), при полуоткрытом – сохраняется
    постоянной. При моделировании показаний датчика присутствия людей в комнате
    может использоваться вычисляемая тем или иным образом случайная величина –
    отклонение от известного расписания пребывания людей в рассматриваемой
    комнате дома (например, жильцы дома могут по каким-либо причинам раньше
    обычного уйти с утра из дома).
    В ходе моделирования должны быть изображены: план дома, наличие в
    каждой комнате людей, положение клапанов обогревателя, а также указаны
    температура в каждой комнате, время суток и день недели, расход топлива на
    текущий момент. Кроме начального задания недельного расписания следует
    допустить также возможность его изменения в ходе экспериментов.