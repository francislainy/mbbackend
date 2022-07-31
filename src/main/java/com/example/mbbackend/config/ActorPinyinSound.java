
package com.example.mbbackend.config;

import lombok.Getter;

@Getter
public enum ActorPinyinSound {

    ZHU("zhu"), CHU("chu"), SHU("shu"), ZH("zh"), CH("ch"), SH("sh"), BI("bi"), PI("pi"), MI("mi"), DI("di"), TI("ti"), NI("ni"),
    LI("li"), JI("ji"), QI("qi"), XI("xi"), BU("bu"), PU("pu"), MU("mu"), FU("fu"), DU("du"), TU("tu"), NU("nu"), LU("lu"), GU("gu"),
    KU("ku"), HU("hu"), RU("ru"), ZU("zu"), CU("cu"), SU("su"), YU("yu"), NU_UMBRAL("nü"), LU_UMBRAL("lü"), JU("ju"), QU("qu"), XU("xu"), B("b"),
    P("p"), M("m"), F("f"), D("d"), T("t"), N("n"), L("l"), G("g"), K("k"), H("h"), R("r"), Z("z"), C("c"), S("s"), Y("y"), W("w");

    private final String sound;

    ActorPinyinSound(String sound) {
        this.sound = sound;
    }
}