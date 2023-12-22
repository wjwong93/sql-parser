MATCH (n) DETACH DELETE n;

MERGE (f0:Estproduction {name: "estpro1110_202311101200"})
SET f0.key="202311101200-estproduction-gh1-1113,202311101200-estproduction-gh1-1114,202311101200-estproduction-gh1-1115", f0.note="TRUE", f0.ts=202311101200
MERGE (f1:Fruit {name: "fruit1111_202311131500"})
SET f1.key="202311111200-fruit-gh1-c11-f111,202311111200-fruit-gh1-c11-f112,202311111200-fruit-gh1-c11-f113", f1.note="simulation", f1.ts=202311131500
MERGE (f0)-[:DEP]->(f1)

MERGE (f2:Estproduction {name: "estpro1110_202311101200"})
SET f2.key="202311101200-estproduction-gh1-1113,202311101200-estproduction-gh1-1114,202311101200-estproduction-gh1-1115", f2.note="TRUE", f2.ts=202311101200
MERGE (f3:Precal {name: "precal_simulation_1111_202311131500"})
SET f3.key="202311111200-precal-gh1-simulation", f3.note="simulation", f3.ts=202311131500
MERGE (f2)-[:INDEP]->(f3)

MERGE (f4:Precal {name: "precal_simulation_1111_202311131500"})
SET f4.key="202311111200-precal-gh1-simulation", f4.note="simulation", f4.ts=202311131500
MERGE (f5:Fruit {name: "fruit1111_202311131500"})
SET f5.key="202311111200-fruit-gh1-c11-f111,202311111200-fruit-gh1-c11-f112,202311111200-fruit-gh1-c11-f113", f5.note="simulation", f5.ts=202311131500
MERGE (f4)-[:DEP]->(f5)

MERGE (f6:Monitor {name: "monitor1110_202311101200"})
SET f6.key="202311101200-monitor-gh1-photo01", f6.note="TRUE", f6.ts=202311101200
MERGE (f7:Precal {name: "precal_m1_1110_202311101200"})
SET f7.key="202311101200-precal-gh1-m1", f7.note="TRUE", f7.ts=202311101200
MERGE (f6)-[:INDEP]->(f7)

MERGE (f8:Precal {name: "precal_m1_1110_202311101200"})
SET f8.key="202311101200-precal-gh1-m1", f8.note="TRUE", f8.ts=202311101200
MERGE (f9:Model {name: "m1ver1110_202311101200"})
SET f9.key="202311101000-model-m1", f9.note="TRUE", f9.ts=202311101200
MERGE (f8)-[:INDEP]->(f9)

MERGE (f10:Monitor {name: "monitor1110_202311101200"})
SET f10.key="202311101200-monitor-gh1-photo01", f10.note="TRUE", f10.ts=202311101200
MERGE (f11:Fruit {name: "fruit1110_202311101200"})
SET f11.key="202311101200-fruit-gh1-c11-f111,202311101200-fruit-gh1-c11-f112,202311101200-fruit-gh1-c11-f113", f11.note="TRUE", f11.ts=202311101200
MERGE (f10)-[:DEP]->(f11)

MERGE (f12:Precal {name: "precal_m1_1110_202311101200"})
SET f12.key="202311101200-precal-gh1-m1", f12.note="TRUE", f12.ts=202311101200
MERGE (f13:Fruit {name: "fruit1110_202311101200"})
SET f13.key="202311101200-fruit-gh1-c11-f111,202311101200-fruit-gh1-c11-f112,202311101200-fruit-gh1-c11-f113", f13.note="TRUE", f13.ts=202311101200
MERGE (f12)-[:DEP]->(f13)

MERGE (f14:Model {name: "m1ver1110_202311101200"})
SET f14.key="202311101000-model-m1", f14.note="TRUE", f14.ts=202311101200
MERGE (f15:Fruit {name: "fruit1110_202311101200"})
SET f15.key="202311101200-fruit-gh1-c11-f111,202311101200-fruit-gh1-c11-f112,202311101200-fruit-gh1-c11-f113", f15.note="TRUE", f15.ts=202311101200
MERGE (f14)-[:DEP]->(f15)

MERGE (f16:Fruit {name: "fruit1110_202311101200"})
SET f16.key="202311101200-fruit-gh1-c11-f111,202311101200-fruit-gh1-c11-f112,202311101200-fruit-gh1-c11-f113", f16.note="TRUE", f16.ts=202311101200
MERGE (f17:Precal {name: "precal_m2_1110_202311101200"})
SET f17.key="202311101200-precal-gh1-m2", f17.note="TRUE", f17.ts=202311101200
MERGE (f16)-[:INDEP]->(f17)

MERGE (f18:Precal {name: "precal_m2_1110_202311101200"})
SET f18.key="202311101200-precal-gh1-m2", f18.note="TRUE", f18.ts=202311101200
MERGE (f19:Model {name: "m2ver1110_202311101200"})
SET f19.key="202311101000-model-m2", f19.note="TRUE", f19.ts=202311101200
MERGE (f18)-[:INDEP]->(f19)

MERGE (f20:Model {name: "m2ver1110_202311101200"})
SET f20.key="202311101000-model-m2", f20.note="TRUE", f20.ts=202311101200
MERGE (f21:Estproduction {name: "estpro1110_202311101200"})
SET f21.key="202311101200-estproduction-gh1-1113,202311101200-estproduction-gh1-1114,202311101200-estproduction-gh1-1115", f21.note="TRUE", f21.ts=202311101200
MERGE (f20)-[:DEP]->(f21)

MERGE (f22:Fruit {name: "fruit1110_202311101200"})
SET f22.key="202311101200-fruit-gh1-c11-f111,202311101200-fruit-gh1-c11-f112,202311101200-fruit-gh1-c11-f113", f22.note="TRUE", f22.ts=202311101200
MERGE (f23:Estproduction {name: "estpro1110_202311101200"})
SET f23.key="202311101200-estproduction-gh1-1113,202311101200-estproduction-gh1-1114,202311101200-estproduction-gh1-1115", f23.note="TRUE", f23.ts=202311101200
MERGE (f22)-[:DEP]->(f23)

MERGE (f24:Precal {name: "precal_m2_1110_202311101200"})
SET f24.key="202311101200-precal-gh1-m2", f24.note="TRUE", f24.ts=202311101200
MERGE (f25:Estproduction {name: "estpro1110_202311101200"})
SET f25.key="202311101200-estproduction-gh1-1113,202311101200-estproduction-gh1-1114,202311101200-estproduction-gh1-1115", f25.note="TRUE", f25.ts=202311101200
MERGE (f24)-[:DEP]->(f25)

MERGE (f26:Sensor {name: "sensor1110_202311101200"})
SET f26.key="202311101200-sensor-gh1-temp-sen11,202311101200-sensor-gh1-temp-sen12,202311101200-sensor-gh1-humid-sen13,202311101200-sensor-gh1-humid-sen14", f26.note="TRUE", f26.ts=202311101200
MERGE (f27:Precal {name: "precal_m2_1110_202311101200"})
SET f27.key="202311101200-precal-gh1-m2", f27.note="TRUE", f27.ts=202311101200
MERGE (f26)-[:INDEP]->(f27)

MERGE (f28:Sensor {name: "sensor1110_202311101200"})
SET f28.key="202311101200-sensor-gh1-temp-sen11,202311101200-sensor-gh1-temp-sen12,202311101200-sensor-gh1-humid-sen13,202311101200-sensor-gh1-humid-sen14", f28.note="TRUE", f28.ts=202311101200
MERGE (f29:Estproduction {name: "estpro1110_202311101200"})
SET f29.key="202311101200-estproduction-gh1-1113,202311101200-estproduction-gh1-1114,202311101200-estproduction-gh1-1115", f29.note="TRUE", f29.ts=202311101200
MERGE (f28)-[:DEP]->(f29)

MERGE (f30:Monitor {name: "monitor1111_202311111200"})
SET f30.key="202311111200-monitor-gh1-photo01", f30.note="TRUE", f30.ts=202311111200
MERGE (f31:Precal {name: "precal_m1_1111_202311111200"})
SET f31.key="202311111200-precal-gh1-m1", f31.note="TRUE", f31.ts=202311111200
MERGE (f30)-[:INDEP]->(f31)

MERGE (f32:Precal {name: "precal_m1_1111_202311111200"})
SET f32.key="202311111200-precal-gh1-m1", f32.note="TRUE", f32.ts=202311111200
MERGE (f33:Model {name: "m1ver1110_202311101200"})
SET f33.key="202311101000-model-m1", f33.note="TRUE", f33.ts=202311101200
MERGE (f32)-[:INDEP]->(f33)

MERGE (f34:Monitor {name: "monitor1111_202311111200"})
SET f34.key="202311111200-monitor-gh1-photo01", f34.note="TRUE", f34.ts=202311111200
MERGE (f35:Fruit {name: "fruit1111_202311111200"})
SET f35.key="202311111200-fruit-gh1-c11-f111,202311111200-fruit-gh1-c11-f112,202311111200-fruit-gh1-c11-f113", f35.note="TRUE", f35.ts=202311111200
MERGE (f34)-[:DEP]->(f35)

MERGE (f36:Precal {name: "precal_m1_1111_202311111200"})
SET f36.key="202311111200-precal-gh1-m1", f36.note="TRUE", f36.ts=202311111200
MERGE (f37:Fruit {name: "fruit1111_202311111200"})
SET f37.key="202311111200-fruit-gh1-c11-f111,202311111200-fruit-gh1-c11-f112,202311111200-fruit-gh1-c11-f113", f37.note="TRUE", f37.ts=202311111200
MERGE (f36)-[:DEP]->(f37)

MERGE (f38:Model {name: "m1ver1110_202311101200"})
SET f38.key="202311101000-model-m1", f38.note="TRUE", f38.ts=202311101200
MERGE (f39:Fruit {name: "fruit1111_202311111200"})
SET f39.key="202311111200-fruit-gh1-c11-f111,202311111200-fruit-gh1-c11-f112,202311111200-fruit-gh1-c11-f113", f39.note="TRUE", f39.ts=202311111200
MERGE (f38)-[:DEP]->(f39)

MERGE (f40:Fruit {name: "fruit1111_202311111200"})
SET f40.key="202311111200-fruit-gh1-c11-f111,202311111200-fruit-gh1-c11-f112,202311111200-fruit-gh1-c11-f113", f40.note="TRUE", f40.ts=202311111200
MERGE (f41:Precal {name: "precal_m2_1111_202311111200"})
SET f41.key="202311111200-precal-gh1-m2", f41.note="TRUE", f41.ts=202311111200
MERGE (f40)-[:INDEP]->(f41)

MERGE (f42:Precal {name: "precal_m2_1111_202311111200"})
SET f42.key="202311111200-precal-gh1-m2", f42.note="TRUE", f42.ts=202311111200
MERGE (f43:Model {name: "m2ver1110_202311101200"})
SET f43.key="202311101000-model-m2", f43.note="TRUE", f43.ts=202311101200
MERGE (f42)-[:INDEP]->(f43)

MERGE (f44:Model {name: "m2ver1110_202311101200"})
SET f44.key="202311101000-model-m2", f44.note="TRUE", f44.ts=202311101200
MERGE (f45:Estproduction {name: "estpro1111_202311111200"})
SET f45.key="202311111200-estproduction-gh1-1113,202311111200-estproduction-gh1-1114,202311111200-estproduction-gh1-1115", f45.note="TRUE", f45.ts=202311111200
MERGE (f44)-[:DEP]->(f45)

MERGE (f46:Fruit {name: "fruit1111_202311111200"})
SET f46.key="202311111200-fruit-gh1-c11-f111,202311111200-fruit-gh1-c11-f112,202311111200-fruit-gh1-c11-f113", f46.note="TRUE", f46.ts=202311111200
MERGE (f47:Estproduction {name: "estpro1111_202311111200"})
SET f47.key="202311111200-estproduction-gh1-1113,202311111200-estproduction-gh1-1114,202311111200-estproduction-gh1-1115", f47.note="TRUE", f47.ts=202311111200
MERGE (f46)-[:DEP]->(f47)

MERGE (f48:Precal {name: "precal_m2_1111_202311111200"})
SET f48.key="202311111200-precal-gh1-m2", f48.note="TRUE", f48.ts=202311111200
MERGE (f49:Estproduction {name: "estpro1111_202311111200"})
SET f49.key="202311111200-estproduction-gh1-1113,202311111200-estproduction-gh1-1114,202311111200-estproduction-gh1-1115", f49.note="TRUE", f49.ts=202311111200
MERGE (f48)-[:DEP]->(f49)

MERGE (f50:Sensor {name: "sensor1111_202311111200"})
SET f50.key="202311111200-sensor-gh1-temp-sen11,202311111200-sensor-gh1-temp-sen12,202311111200-sensor-gh1-humid-sen13,202311111200-sensor-gh1-humid-sen14", f50.note="TRUE", f50.ts=202311111200
MERGE (f51:Precal {name: "precal_m2_1111_202311111200"})
SET f51.key="202311111200-precal-gh1-m2", f51.note="TRUE", f51.ts=202311111200
MERGE (f50)-[:INDEP]->(f51)

MERGE (f52:Sensor {name: "sensor1111_202311111200"})
SET f52.key="202311111200-sensor-gh1-temp-sen11,202311111200-sensor-gh1-temp-sen12,202311111200-sensor-gh1-humid-sen13,202311111200-sensor-gh1-humid-sen14", f52.note="TRUE", f52.ts=202311111200
MERGE (f53:Estproduction {name: "estpro1111_202311111200"})
SET f53.key="202311111200-estproduction-gh1-1113,202311111200-estproduction-gh1-1114,202311111200-estproduction-gh1-1115", f53.note="TRUE", f53.ts=202311111200
MERGE (f52)-[:DEP]->(f53)

MERGE (f54:Monitor {name: "monitor1112_202311121200"})
SET f54.key="202311121200-monitor-gh1-photo01", f54.note="TRUE", f54.ts=202311121200
MERGE (f55:Precal {name: "precal_m1_1112_202311121200"})
SET f55.key="202311121200-precal-gh1-m1", f55.note="TRUE", f55.ts=202311121200
MERGE (f54)-[:INDEP]->(f55)

MERGE (f56:Precal {name: "precal_m1_1112_202311121200"})
SET f56.key="202311121200-precal-gh1-m1", f56.note="TRUE", f56.ts=202311121200
MERGE (f57:Model {name: "m1ver1110_202311101200"})
SET f57.key="202311101000-model-m1", f57.note="TRUE", f57.ts=202311101200
MERGE (f56)-[:INDEP]->(f57)

MERGE (f58:Monitor {name: "monitor1112_202311121200"})
SET f58.key="202311121200-monitor-gh1-photo01", f58.note="TRUE", f58.ts=202311121200
MERGE (f59:Fruit {name: "fruit1112_202311121200"})
SET f59.key="202311121200-fruit-gh1-c11-f111,202311121200-fruit-gh1-c11-f112,202311121200-fruit-gh1-c11-f113", f59.note="TRUE", f59.ts=202311121200
MERGE (f58)-[:DEP]->(f59)

MERGE (f60:Precal {name: "precal_m1_1112_202311121200"})
SET f60.key="202311121200-precal-gh1-m1", f60.note="TRUE", f60.ts=202311121200
MERGE (f61:Fruit {name: "fruit1112_202311121200"})
SET f61.key="202311121200-fruit-gh1-c11-f111,202311121200-fruit-gh1-c11-f112,202311121200-fruit-gh1-c11-f113", f61.note="TRUE", f61.ts=202311121200
MERGE (f60)-[:DEP]->(f61)

MERGE (f62:Model {name: "m1ver1110_202311101200"})
SET f62.key="202311101000-model-m1", f62.note="TRUE", f62.ts=202311101200
MERGE (f63:Fruit {name: "fruit1112_202311121200"})
SET f63.key="202311121200-fruit-gh1-c11-f111,202311121200-fruit-gh1-c11-f112,202311121200-fruit-gh1-c11-f113", f63.note="TRUE", f63.ts=202311121200
MERGE (f62)-[:DEP]->(f63)

MERGE (f64:Fruit {name: "fruit1112_202311121200"})
SET f64.key="202311121200-fruit-gh1-c11-f111,202311121200-fruit-gh1-c11-f112,202311121200-fruit-gh1-c11-f113", f64.note="TRUE", f64.ts=202311121200
MERGE (f65:Precal {name: "precal_m2_1112_202311121200"})
SET f65.key="202311121200-precal-gh1-m2", f65.note="TRUE", f65.ts=202311121200
MERGE (f64)-[:INDEP]->(f65)

MERGE (f66:Precal {name: "precal_m2_1112_202311121200"})
SET f66.key="202311121200-precal-gh1-m2", f66.note="TRUE", f66.ts=202311121200
MERGE (f67:Model {name: "m2ver1112_202311121200"})
SET f67.key="202311121000-model-m2", f67.note="TRUE", f67.ts=202311121200
MERGE (f66)-[:INDEP]->(f67)

MERGE (f68:Model {name: "m2ver1112_202311121200"})
SET f68.key="202311121000-model-m2", f68.note="TRUE", f68.ts=202311121200
MERGE (f69:Estproduction {name: "estpro1112_202311121200"})
SET f69.key="202311121200-estproduction-gh1-1113,202311121200-estproduction-gh1-1114,202311121200-estproduction-gh1-1115", f69.note="TRUE", f69.ts=202311121200
MERGE (f68)-[:DEP]->(f69)

MERGE (f70:Fruit {name: "fruit1112_202311121200"})
SET f70.key="202311121200-fruit-gh1-c11-f111,202311121200-fruit-gh1-c11-f112,202311121200-fruit-gh1-c11-f113", f70.note="TRUE", f70.ts=202311121200
MERGE (f71:Estproduction {name: "estpro1112_202311121200"})
SET f71.key="202311121200-estproduction-gh1-1113,202311121200-estproduction-gh1-1114,202311121200-estproduction-gh1-1115", f71.note="TRUE", f71.ts=202311121200
MERGE (f70)-[:DEP]->(f71)

MERGE (f72:Precal {name: "precal_m2_1112_202311121200"})
SET f72.key="202311121200-precal-gh1-m2", f72.note="TRUE", f72.ts=202311121200
MERGE (f73:Estproduction {name: "estpro1112_202311121200"})
SET f73.key="202311121200-estproduction-gh1-1113,202311121200-estproduction-gh1-1114,202311121200-estproduction-gh1-1115", f73.note="TRUE", f73.ts=202311121200
MERGE (f72)-[:DEP]->(f73)

MERGE (f74:Sensor {name: "sensor1112_202311121200"})
SET f74.key="202311121200-sensor-gh1-temp-sen11,202311121200-sensor-gh1-temp-sen12,202311121200-sensor-gh1-humid-sen13,202311121200-sensor-gh1-humid-sen14", f74.note="TRUE", f74.ts=202311121200
MERGE (f75:Precal {name: "precal_m2_1112_202311121200"})
SET f75.key="202311121200-precal-gh1-m2", f75.note="TRUE", f75.ts=202311121200
MERGE (f74)-[:INDEP]->(f75)

MERGE (f76:Sensor {name: "sensor1112_202311121200"})
SET f76.key="202311121200-sensor-gh1-temp-sen11,202311121200-sensor-gh1-temp-sen12,202311121200-sensor-gh1-humid-sen13,202311121200-sensor-gh1-humid-sen14", f76.note="TRUE", f76.ts=202311121200
MERGE (f77:Estproduction {name: "estpro1112_202311121200"})
SET f77.key="202311121200-estproduction-gh1-1113,202311121200-estproduction-gh1-1114,202311121200-estproduction-gh1-1115", f77.note="TRUE", f77.ts=202311121200
MERGE (f76)-[:DEP]->(f77)

MERGE (f78:Monitor {name: "monitor1103_202311031200"})
SET f78.key="202311031200-monitor-gh1-photo01", f78.ts=202311031200
MERGE (f79:Precal {name: "precal_m1_1103_202311031200"})
SET f79.key="202311031200-precal-gh1-m1", f79.ts=202311031200
MERGE (f78)-[:INDEP]->(f79)

MERGE (f80:Precal {name: "precal_m1_1103_202311031200"})
SET f80.key="202311031200-precal-gh1-m1", f80.ts=202311031200
MERGE (f81:Model {name: "m1ver1103_202311031200"})
SET f81.key="202311031000-model-m1", f81.ts=202311031200
MERGE (f80)-[:INDEP]->(f81)

MERGE (f82:Monitor {name: "monitor1103_202311031200"})
SET f82.key="202311031200-monitor-gh1-photo01", f82.ts=202311031200
MERGE (f83:Fruit {name: "fruit1103_202311031200"})
SET f83.key="202311031200-fruit-gh1-c11-f111,202311031200-fruit-gh1-c11-f112,202311031200-fruit-gh1-c11-f113", f83.ts=202311031200
MERGE (f82)-[:DEP]->(f83)

MERGE (f84:Precal {name: "precal_m1_1103_202311031200"})
SET f84.key="202311031200-precal-gh1-m1", f84.ts=202311031200
MERGE (f85:Fruit {name: "fruit1103_202311031200"})
SET f85.key="202311031200-fruit-gh1-c11-f111,202311031200-fruit-gh1-c11-f112,202311031200-fruit-gh1-c11-f113", f85.ts=202311031200
MERGE (f84)-[:DEP]->(f85)

MERGE (f86:Model {name: "m1ver1103_202311031200"})
SET f86.key="202311031000-model-m1", f86.ts=202311031200
MERGE (f87:Fruit {name: "fruit1103_202311031200"})
SET f87.key="202311031200-fruit-gh1-c11-f111,202311031200-fruit-gh1-c11-f112,202311031200-fruit-gh1-c11-f113", f87.ts=202311031200
MERGE (f86)-[:DEP]->(f87)

MERGE (f88:Fruit {name: "fruit1103_202311031200"})
SET f88.key="202311031200-fruit-gh1-c11-f111,202311031200-fruit-gh1-c11-f112,202311031200-fruit-gh1-c11-f113", f88.ts=202311031200
MERGE (f89:Precal {name: "precal_m2_1103_202311031200"})
SET f89.key="202311031200-precal-gh1-m2", f89.ts=202311031200
MERGE (f88)-[:INDEP]->(f89)

MERGE (f90:Precal {name: "precal_m2_1103_202311031200"})
SET f90.key="202311031200-precal-gh1-m2", f90.ts=202311031200
MERGE (f91:Model {name: "m2ver1103_202311031200"})
SET f91.key="202311031000-model-m2", f91.ts=202311031200
MERGE (f90)-[:INDEP]->(f91)

MERGE (f92:Model {name: "m2ver1103_202311031200"})
SET f92.key="202311031000-model-m2", f92.ts=202311031200
MERGE (f93:Estproduction {name: "estpro1103_202311031200"})
SET f93.key="202311031200-estproduction-gh1-1110,202311031200-estproduction-gh1-1111,202311031200-estproduction-gh1-1112", f93.ts=202311031200
MERGE (f92)-[:DEP]->(f93)

MERGE (f94:Fruit {name: "fruit1103_202311031200"})
SET f94.key="202311031200-fruit-gh1-c11-f111,202311031200-fruit-gh1-c11-f112,202311031200-fruit-gh1-c11-f113", f94.ts=202311031200
MERGE (f95:Estproduction {name: "estpro1103_202311031200"})
SET f95.key="202311031200-estproduction-gh1-1110,202311031200-estproduction-gh1-1111,202311031200-estproduction-gh1-1112", f95.ts=202311031200
MERGE (f94)-[:DEP]->(f95)

MERGE (f96:Precal {name: "precal_m2_1103_202311031200"})
SET f96.key="202311031200-precal-gh1-m2", f96.ts=202311031200
MERGE (f97:Estproduction {name: "estpro1103_202311031200"})
SET f97.key="202311031200-estproduction-gh1-1110,202311031200-estproduction-gh1-1111,202311031200-estproduction-gh1-1112", f97.ts=202311031200
MERGE (f96)-[:DEP]->(f97)

MERGE (f98:Sensor {name: "sensor1103_202311031200"})
SET f98.key="202311031200-sensor-gh1-temp-sen11,202311031200-sensor-gh1-temp-sen12,202311031200-sensor-gh1-humid-sen13,202311031200-sensor-gh1-humid-sen14", f98.ts=202311031200
MERGE (f99:Precal {name: "precal_m2_1103_202311031200"})
SET f99.key="202311031200-precal-gh1-m2", f99.ts=202311031200
MERGE (f98)-[:INDEP]->(f99)

MERGE (f100:Sensor {name: "sensor1103_202311031200"})
SET f100.key="202311031200-sensor-gh1-temp-sen11,202311031200-sensor-gh1-temp-sen12,202311031200-sensor-gh1-humid-sen13,202311031200-sensor-gh1-humid-sen14", f100.ts=202311031200
MERGE (f101:Estproduction {name: "estpro1103_202311031200"})
SET f101.key="202311031200-estproduction-gh1-1110,202311031200-estproduction-gh1-1111,202311031200-estproduction-gh1-1112", f101.ts=202311031200
MERGE (f100)-[:DEP]->(f101)

MERGE (f102:Monitor {name: "monitor1104_202311041200"})
SET f102.key="202311041200-monitor-gh1-photo01", f102.ts=202311041200
MERGE (f103:Precal {name: "precal_m1_1104_202311041200"})
SET f103.key="202311041200-precal-gh1-m1", f103.ts=202311041200
MERGE (f102)-[:INDEP]->(f103)

MERGE (f104:Precal {name: "precal_m1_1104_202311041200"})
SET f104.key="202311041200-precal-gh1-m1", f104.ts=202311041200
MERGE (f105:Model {name: "m1ver1103_202311031200"})
SET f105.key="202311031000-model-m1", f105.ts=202311031200
MERGE (f104)-[:INDEP]->(f105)

MERGE (f106:Monitor {name: "monitor1104_202311041200"})
SET f106.key="202311041200-monitor-gh1-photo01", f106.ts=202311041200
MERGE (f107:Fruit {name: "fruit1104_202311041200"})
SET f107.key="202311041200-fruit-gh1-c11-f111,202311041200-fruit-gh1-c11-f112,202311041200-fruit-gh1-c11-f113", f107.ts=202311041200
MERGE (f106)-[:DEP]->(f107)

MERGE (f108:Precal {name: "precal_m1_1104_202311041200"})
SET f108.key="202311041200-precal-gh1-m1", f108.ts=202311041200
MERGE (f109:Fruit {name: "fruit1104_202311041200"})
SET f109.key="202311041200-fruit-gh1-c11-f111,202311041200-fruit-gh1-c11-f112,202311041200-fruit-gh1-c11-f113", f109.ts=202311041200
MERGE (f108)-[:DEP]->(f109)

MERGE (f110:Model {name: "m1ver1103_202311031200"})
SET f110.key="202311031000-model-m1", f110.ts=202311031200
MERGE (f111:Fruit {name: "fruit1104_202311041200"})
SET f111.key="202311041200-fruit-gh1-c11-f111,202311041200-fruit-gh1-c11-f112,202311041200-fruit-gh1-c11-f113", f111.ts=202311041200
MERGE (f110)-[:DEP]->(f111)

MERGE (f112:Fruit {name: "fruit1104_202311041200"})
SET f112.key="202311041200-fruit-gh1-c11-f111,202311041200-fruit-gh1-c11-f112,202311041200-fruit-gh1-c11-f113", f112.ts=202311041200
MERGE (f113:Precal {name: "precal_m2_1104_202311041200"})
SET f113.key="202311041200-precal-gh1-m2", f113.ts=202311041200
MERGE (f112)-[:INDEP]->(f113)

MERGE (f114:Precal {name: "precal_m2_1104_202311041200"})
SET f114.key="202311041200-precal-gh1-m2", f114.ts=202311041200
MERGE (f115:Model {name: "m2ver1103_202311031200"})
SET f115.key="202311031000-model-m2", f115.ts=202311031200
MERGE (f114)-[:INDEP]->(f115)

MERGE (f116:Model {name: "m2ver1103_202311031200"})
SET f116.key="202311031000-model-m2", f116.ts=202311031200
MERGE (f117:Estproduction {name: "estpro1104_202311041200"})
SET f117.key="202311041200-estproduction-gh1-1110,202311041200-estproduction-gh1-1111,202311041200-estproduction-gh1-1112", f117.ts=202311041200
MERGE (f116)-[:DEP]->(f117)

MERGE (f118:Fruit {name: "fruit1104_202311041200"})
SET f118.key="202311041200-fruit-gh1-c11-f111,202311041200-fruit-gh1-c11-f112,202311041200-fruit-gh1-c11-f113", f118.ts=202311041200
MERGE (f119:Estproduction {name: "estpro1104_202311041200"})
SET f119.key="202311041200-estproduction-gh1-1110,202311041200-estproduction-gh1-1111,202311041200-estproduction-gh1-1112", f119.ts=202311041200
MERGE (f118)-[:DEP]->(f119)

MERGE (f120:Precal {name: "precal_m2_1104_202311041200"})
SET f120.key="202311041200-precal-gh1-m2", f120.ts=202311041200
MERGE (f121:Estproduction {name: "estpro1104_202311041200"})
SET f121.key="202311041200-estproduction-gh1-1110,202311041200-estproduction-gh1-1111,202311041200-estproduction-gh1-1112", f121.ts=202311041200
MERGE (f120)-[:DEP]->(f121)

MERGE (f122:Sensor {name: "sensor1104_202311041200"})
SET f122.key="202311041200-sensor-gh1-temp-sen11,202311041200-sensor-gh1-temp-sen12,202311041200-sensor-gh1-humid-sen13,202311041200-sensor-gh1-humid-sen14", f122.ts=202311041200
MERGE (f123:Precal {name: "precal_m2_1104_202311041200"})
SET f123.key="202311041200-precal-gh1-m2", f123.ts=202311041200
MERGE (f122)-[:INDEP]->(f123)

MERGE (f124:Sensor {name: "sensor1104_202311041200"})
SET f124.key="202311041200-sensor-gh1-temp-sen11,202311041200-sensor-gh1-temp-sen12,202311041200-sensor-gh1-humid-sen13,202311041200-sensor-gh1-humid-sen14", f124.ts=202311041200
MERGE (f125:Estproduction {name: "estpro1104_202311041200"})
SET f125.key="202311041200-estproduction-gh1-1110,202311041200-estproduction-gh1-1111,202311041200-estproduction-gh1-1112", f125.ts=202311041200
MERGE (f124)-[:DEP]->(f125)

MERGE (f126:Monitor {name: "monitor1105_202311051200"})
SET f126.key="202311051200-monitor-gh1-photo01", f126.ts=202311051200
MERGE (f127:Precal {name: "precal_m1_1105_202311051200"})
SET f127.key="202311051200-precal-gh1-m1", f127.ts=202311051200
MERGE (f126)-[:INDEP]->(f127)

MERGE (f128:Precal {name: "precal_m1_1105_202311051200"})
SET f128.key="202311051200-precal-gh1-m1", f128.ts=202311051200
MERGE (f129:Model {name: "m1ver1103_202311031200"})
SET f129.key="202311031000-model-m1", f129.ts=202311031200
MERGE (f128)-[:INDEP]->(f129)

MERGE (f130:Monitor {name: "monitor1105_202311051200"})
SET f130.key="202311051200-monitor-gh1-photo01", f130.ts=202311051200
MERGE (f131:Fruit {name: "fruit1105_202311051200"})
SET f131.key="202311051200-fruit-gh1-c11-f111,202311051200-fruit-gh1-c11-f112,202311051200-fruit-gh1-c11-f113", f131.ts=202311051200
MERGE (f130)-[:DEP]->(f131)

MERGE (f132:Precal {name: "precal_m1_1105_202311051200"})
SET f132.key="202311051200-precal-gh1-m1", f132.ts=202311051200
MERGE (f133:Fruit {name: "fruit1105_202311051200"})
SET f133.key="202311051200-fruit-gh1-c11-f111,202311051200-fruit-gh1-c11-f112,202311051200-fruit-gh1-c11-f113", f133.ts=202311051200
MERGE (f132)-[:DEP]->(f133)

MERGE (f134:Model {name: "m1ver1103_202311031200"})
SET f134.key="202311031000-model-m1", f134.ts=202311031200
MERGE (f135:Fruit {name: "fruit1105_202311051200"})
SET f135.key="202311051200-fruit-gh1-c11-f111,202311051200-fruit-gh1-c11-f112,202311051200-fruit-gh1-c11-f113", f135.ts=202311051200
MERGE (f134)-[:DEP]->(f135)

MERGE (f136:Fruit {name: "fruit1105_202311051200"})
SET f136.key="202311051200-fruit-gh1-c11-f111,202311051200-fruit-gh1-c11-f112,202311051200-fruit-gh1-c11-f113", f136.ts=202311051200
MERGE (f137:Precal {name: "precal_m2_1105_202311051200"})
SET f137.key="202311051200-precal-gh1-m2", f137.ts=202311051200
MERGE (f136)-[:INDEP]->(f137)

MERGE (f138:Precal {name: "precal_m2_1105_202311051200"})
SET f138.key="202311051200-precal-gh1-m2", f138.ts=202311051200
MERGE (f139:Model {name: "m2ver1105_202311051200"})
SET f139.key="202311051000-model-m2", f139.ts=202311051200
MERGE (f138)-[:INDEP]->(f139)

MERGE (f140:Model {name: "m2ver1105_202311051200"})
SET f140.key="202311051000-model-m2", f140.ts=202311051200
MERGE (f141:Estproduction {name: "estpro1105_202311051200"})
SET f141.key="202311051200-estproduction-gh1-1110,202311051200-estproduction-gh1-1111,202311051200-estproduction-gh1-1112", f141.ts=202311051200
MERGE (f140)-[:DEP]->(f141)

MERGE (f142:Fruit {name: "fruit1105_202311051200"})
SET f142.key="202311051200-fruit-gh1-c11-f111,202311051200-fruit-gh1-c11-f112,202311051200-fruit-gh1-c11-f113", f142.ts=202311051200
MERGE (f143:Estproduction {name: "estpro1105_202311051200"})
SET f143.key="202311051200-estproduction-gh1-1110,202311051200-estproduction-gh1-1111,202311051200-estproduction-gh1-1112", f143.ts=202311051200
MERGE (f142)-[:DEP]->(f143)

MERGE (f144:Precal {name: "precal_m2_1105_202311051200"})
SET f144.key="202311051200-precal-gh1-m2", f144.ts=202311051200
MERGE (f145:Estproduction {name: "estpro1105_202311051200"})
SET f145.key="202311051200-estproduction-gh1-1110,202311051200-estproduction-gh1-1111,202311051200-estproduction-gh1-1112", f145.ts=202311051200
MERGE (f144)-[:DEP]->(f145)

MERGE (f146:Sensor {name: "sensor1105_202311051200"})
SET f146.key="202311051200-sensor-gh1-temp-sen11,202311051200-sensor-gh1-temp-sen12,202311051200-sensor-gh1-humid-sen13,202311051200-sensor-gh1-humid-sen14", f146.ts=202311051200
MERGE (f147:Precal {name: "precal_m2_1105_202311051200"})
SET f147.key="202311051200-precal-gh1-m2", f147.ts=202311051200
MERGE (f146)-[:INDEP]->(f147)

MERGE (f148:Sensor {name: "sensor1105_202311051200"})
SET f148.key="202311051200-sensor-gh1-temp-sen11,202311051200-sensor-gh1-temp-sen12,202311051200-sensor-gh1-humid-sen13,202311051200-sensor-gh1-humid-sen14", f148.ts=202311051200
MERGE (f149:Estproduction {name: "estpro1105_202311051200"})
SET f149.key="202311051200-estproduction-gh1-1110,202311051200-estproduction-gh1-1111,202311051200-estproduction-gh1-1112", f149.ts=202311051200
MERGE (f148)-[:DEP]->(f149)

MERGE (f150:Sensor {name: "sensor1103_202311031200"})
SET f150.key="202311031200-sensor-gh1-temp-sen11,202311031200-sensor-gh1-temp-sen12,202311031200-sensor-gh1-humid-sen13,202311031200-sensor-gh1-humid-sen14", f150.ts=202311031200
MERGE (f151:Model {name: "m2ver1105_202311051200"})
SET f151.key="202311051000-model-m2", f151.ts=202311051200
MERGE (f150)-[:DEP]->(f151)

MERGE (f152:Sensor {name: "sensor1104_202311041200"})
SET f152.key="202311041200-sensor-gh1-temp-sen11,202311041200-sensor-gh1-temp-sen12,202311041200-sensor-gh1-humid-sen13,202311041200-sensor-gh1-humid-sen14", f152.ts=202311041200
MERGE (f153:Model {name: "m2ver1105_202311051200"})
SET f153.key="202311051000-model-m2", f153.ts=202311051200
MERGE (f152)-[:DEP]->(f153)

MERGE (f154:Monitor {name: "monitor1106_202311061200"})
SET f154.key="202311061200-monitor-gh1-photo01", f154.note="TRUE", f154.ts=202311061200
MERGE (f155:Precal {name: "precal_m1_1106_202311061200"})
SET f155.key="202311061200-precal-gh1-m1", f155.note="TRUE", f155.ts=202311061200
MERGE (f154)-[:INDEP]->(f155)

MERGE (f156:Precal {name: "precal_m1_1106_202311061200"})
SET f156.key="202311061200-precal-gh1-m1", f156.note="TRUE", f156.ts=202311061200
MERGE (f157:Model {name: "m1ver1106_202311061200"})
SET f157.key="202311061000-model-m1", f157.note="TRUE", f157.ts=202311061200
MERGE (f156)-[:INDEP]->(f157)

MERGE (f158:Monitor {name: "monitor1106_202311061200"})
SET f158.key="202311061200-monitor-gh1-photo01", f158.note="TRUE", f158.ts=202311061200
MERGE (f159:Fruit {name: "fruit1106_202311061200"})
SET f159.key="202311061200-fruit-gh1-c11-f111,202311061200-fruit-gh1-c11-f112,202311061200-fruit-gh1-c11-f113", f159.note="TRUE", f159.ts=202311061200
MERGE (f158)-[:DEP]->(f159)

MERGE (f160:Precal {name: "precal_m1_1106_202311061200"})
SET f160.key="202311061200-precal-gh1-m1", f160.note="TRUE", f160.ts=202311061200
MERGE (f161:Fruit {name: "fruit1106_202311061200"})
SET f161.key="202311061200-fruit-gh1-c11-f111,202311061200-fruit-gh1-c11-f112,202311061200-fruit-gh1-c11-f113", f161.note="TRUE", f161.ts=202311061200
MERGE (f160)-[:DEP]->(f161)

MERGE (f162:Model {name: "m1ver1106_202311061200"})
SET f162.key="202311061000-model-m1", f162.note="TRUE", f162.ts=202311061200
MERGE (f163:Fruit {name: "fruit1106_202311061200"})
SET f163.key="202311061200-fruit-gh1-c11-f111,202311061200-fruit-gh1-c11-f112,202311061200-fruit-gh1-c11-f113", f163.note="TRUE", f163.ts=202311061200
MERGE (f162)-[:DEP]->(f163)

MERGE (f164:Fruit {name: "fruit1106_202311061200"})
SET f164.key="202311061200-fruit-gh1-c11-f111,202311061200-fruit-gh1-c11-f112,202311061200-fruit-gh1-c11-f113", f164.note="TRUE", f164.ts=202311061200
MERGE (f165:Precal {name: "precal_m2_1106_202311061200"})
SET f165.key="202311061200-precal-gh1-m2", f165.note="TRUE", f165.ts=202311061200
MERGE (f164)-[:INDEP]->(f165)

MERGE (f166:Precal {name: "precal_m2_1106_202311061200"})
SET f166.key="202311061200-precal-gh1-m2", f166.note="TRUE", f166.ts=202311061200
MERGE (f167:Model {name: "m2ver1106_202311061200"})
SET f167.key="202311061000-model-m2", f167.note="TRUE", f167.ts=202311061200
MERGE (f166)-[:INDEP]->(f167)

MERGE (f168:Model {name: "m2ver1106_202311061200"})
SET f168.key="202311061000-model-m2", f168.note="TRUE", f168.ts=202311061200
MERGE (f169:Estproduction {name: "estpro1106_202311061200"})
SET f169.key="202311061200-estproduction-gh1-1110,202311061200-estproduction-gh1-1111,202311061200-estproduction-gh1-1112", f169.note="TRUE", f169.ts=202311061200
MERGE (f168)-[:DEP]->(f169)

MERGE (f170:Fruit {name: "fruit1106_202311061200"})
SET f170.key="202311061200-fruit-gh1-c11-f111,202311061200-fruit-gh1-c11-f112,202311061200-fruit-gh1-c11-f113", f170.note="TRUE", f170.ts=202311061200
MERGE (f171:Estproduction {name: "estpro1106_202311061200"})
SET f171.key="202311061200-estproduction-gh1-1110,202311061200-estproduction-gh1-1111,202311061200-estproduction-gh1-1112", f171.note="TRUE", f171.ts=202311061200
MERGE (f170)-[:DEP]->(f171)

MERGE (f172:Precal {name: "precal_m2_1106_202311061200"})
SET f172.key="202311061200-precal-gh1-m2", f172.note="TRUE", f172.ts=202311061200
MERGE (f173:Estproduction {name: "estpro1106_202311061200"})
SET f173.key="202311061200-estproduction-gh1-1110,202311061200-estproduction-gh1-1111,202311061200-estproduction-gh1-1112", f173.note="TRUE", f173.ts=202311061200
MERGE (f172)-[:DEP]->(f173)

MERGE (f174:Sensor {name: "sensor1106_202311061200"})
SET f174.key="202311061200-sensor-gh1-temp-sen11,202311061200-sensor-gh1-temp-sen12,202311061200-sensor-gh1-humid-sen13,202311061200-sensor-gh1-humid-sen14", f174.note="TRUE", f174.ts=202311061200
MERGE (f175:Precal {name: "precal_m2_1106_202311061200"})
SET f175.key="202311061200-precal-gh1-m2", f175.note="TRUE", f175.ts=202311061200
MERGE (f174)-[:INDEP]->(f175)

MERGE (f176:Sensor {name: "sensor1106_202311061200"})
SET f176.key="202311061200-sensor-gh1-temp-sen11,202311061200-sensor-gh1-temp-sen12,202311061200-sensor-gh1-humid-sen13,202311061200-sensor-gh1-humid-sen14", f176.note="TRUE", f176.ts=202311061200
MERGE (f177:Estproduction {name: "estpro1106_202311061200"})
SET f177.key="202311061200-estproduction-gh1-1110,202311061200-estproduction-gh1-1111,202311061200-estproduction-gh1-1112", f177.note="TRUE", f177.ts=202311061200
MERGE (f176)-[:DEP]->(f177)

MERGE (f178:Monitor {name: "monitor1107_202311071200"})
SET f178.key="202311071200-monitor-gh1-photo01", f178.note="TRUE", f178.ts=202311071200
MERGE (f179:Precal {name: "precal_m1_1107_202311071200"})
SET f179.key="202311071200-precal-gh1-m1", f179.note="TRUE", f179.ts=202311071200
MERGE (f178)-[:INDEP]->(f179)

MERGE (f180:Precal {name: "precal_m1_1107_202311071200"})
SET f180.key="202311071200-precal-gh1-m1", f180.note="TRUE", f180.ts=202311071200
MERGE (f181:Model {name: "m1ver1106_202311061200"})
SET f181.key="202311061000-model-m1", f181.note="TRUE", f181.ts=202311061200
MERGE (f180)-[:INDEP]->(f181)

MERGE (f182:Monitor {name: "monitor1107_202311071200"})
SET f182.key="202311071200-monitor-gh1-photo01", f182.note="TRUE", f182.ts=202311071200
MERGE (f183:Fruit {name: "fruit1107_202311071200"})
SET f183.key="202311071200-fruit-gh1-c11-f111,202311071200-fruit-gh1-c11-f112,202311071200-fruit-gh1-c11-f113", f183.note="TRUE", f183.ts=202311071200
MERGE (f182)-[:DEP]->(f183)

MERGE (f184:Precal {name: "precal_m1_1107_202311071200"})
SET f184.key="202311071200-precal-gh1-m1", f184.note="TRUE", f184.ts=202311071200
MERGE (f185:Fruit {name: "fruit1107_202311071200"})
SET f185.key="202311071200-fruit-gh1-c11-f111,202311071200-fruit-gh1-c11-f112,202311071200-fruit-gh1-c11-f113", f185.note="TRUE", f185.ts=202311071200
MERGE (f184)-[:DEP]->(f185)

MERGE (f186:Model {name: "m1ver1106_202311061200"})
SET f186.key="202311061000-model-m1", f186.note="TRUE", f186.ts=202311061200
MERGE (f187:Fruit {name: "fruit1107_202311071200"})
SET f187.key="202311071200-fruit-gh1-c11-f111,202311071200-fruit-gh1-c11-f112,202311071200-fruit-gh1-c11-f113", f187.note="TRUE", f187.ts=202311071200
MERGE (f186)-[:DEP]->(f187)

MERGE (f188:Fruit {name: "fruit1107_202311071200"})
SET f188.key="202311071200-fruit-gh1-c11-f111,202311071200-fruit-gh1-c11-f112,202311071200-fruit-gh1-c11-f113", f188.note="TRUE", f188.ts=202311071200
MERGE (f189:Precal {name: "precal_m2_1107_202311071200"})
SET f189.key="202311071200-precal-gh1-m2", f189.note="TRUE", f189.ts=202311071200
MERGE (f188)-[:INDEP]->(f189)

MERGE (f190:Precal {name: "precal_m2_1107_202311071200"})
SET f190.key="202311071200-precal-gh1-m2", f190.note="TRUE", f190.ts=202311071200
MERGE (f191:Model {name: "m2ver1106_202311061200"})
SET f191.key="202311061000-model-m2", f191.note="TRUE", f191.ts=202311061200
MERGE (f190)-[:INDEP]->(f191)

MERGE (f192:Model {name: "m2ver1106_202311061200"})
SET f192.key="202311061000-model-m2", f192.note="TRUE", f192.ts=202311061200
MERGE (f193:Estproduction {name: "estpro1107_202311071200"})
SET f193.key="202311071200-estproduction-gh1-1110,202311071200-estproduction-gh1-1111,202311071200-estproduction-gh1-1112", f193.note="TRUE", f193.ts=202311071200
MERGE (f192)-[:DEP]->(f193)

MERGE (f194:Fruit {name: "fruit1107_202311071200"})
SET f194.key="202311071200-fruit-gh1-c11-f111,202311071200-fruit-gh1-c11-f112,202311071200-fruit-gh1-c11-f113", f194.note="TRUE", f194.ts=202311071200
MERGE (f195:Estproduction {name: "estpro1107_202311071200"})
SET f195.key="202311071200-estproduction-gh1-1110,202311071200-estproduction-gh1-1111,202311071200-estproduction-gh1-1112", f195.note="TRUE", f195.ts=202311071200
MERGE (f194)-[:DEP]->(f195)

MERGE (f196:Precal {name: "precal_m2_1107_202311071200"})
SET f196.key="202311071200-precal-gh1-m2", f196.note="TRUE", f196.ts=202311071200
MERGE (f197:Estproduction {name: "estpro1107_202311071200"})
SET f197.key="202311071200-estproduction-gh1-1110,202311071200-estproduction-gh1-1111,202311071200-estproduction-gh1-1112", f197.note="TRUE", f197.ts=202311071200
MERGE (f196)-[:DEP]->(f197)

MERGE (f198:Sensor {name: "sensor1107_202311071200"})
SET f198.key="202311071200-sensor-gh1-temp-sen11,202311071200-sensor-gh1-temp-sen12,202311071200-sensor-gh1-humid-sen13,202311071200-sensor-gh1-humid-sen14", f198.note="TRUE", f198.ts=202311071200
MERGE (f199:Precal {name: "precal_m2_1107_202311071200"})
SET f199.key="202311071200-precal-gh1-m2", f199.note="TRUE", f199.ts=202311071200
MERGE (f198)-[:INDEP]->(f199)

MERGE (f200:Sensor {name: "sensor1107_202311071200"})
SET f200.key="202311071200-sensor-gh1-temp-sen11,202311071200-sensor-gh1-temp-sen12,202311071200-sensor-gh1-humid-sen13,202311071200-sensor-gh1-humid-sen14", f200.note="TRUE", f200.ts=202311071200
MERGE (f201:Estproduction {name: "estpro1107_202311071200"})
SET f201.key="202311071200-estproduction-gh1-1110,202311071200-estproduction-gh1-1111,202311071200-estproduction-gh1-1112", f201.note="TRUE", f201.ts=202311071200
MERGE (f200)-[:DEP]->(f201)

MERGE (f202:Monitor {name: "monitor1108_202311081200"})
SET f202.key="202311081200-monitor-gh1-photo01", f202.note="TRUE", f202.ts=202311081200
MERGE (f203:Precal {name: "precal_m1_1108_202311081200"})
SET f203.key="202311081200-precal-gh1-m1", f203.note="TRUE", f203.ts=202311081200
MERGE (f202)-[:INDEP]->(f203)

MERGE (f204:Precal {name: "precal_m1_1108_202311081200"})
SET f204.key="202311081200-precal-gh1-m1", f204.note="TRUE", f204.ts=202311081200
MERGE (f205:Model {name: "m1ver1106_202311061200"})
SET f205.key="202311061000-model-m1", f205.note="TRUE", f205.ts=202311061200
MERGE (f204)-[:INDEP]->(f205)

MERGE (f206:Monitor {name: "monitor1108_202311081200"})
SET f206.key="202311081200-monitor-gh1-photo01", f206.note="TRUE", f206.ts=202311081200
MERGE (f207:Fruit {name: "fruit1108_202311081200"})
SET f207.key="202311081200-fruit-gh1-c11-f111,202311081200-fruit-gh1-c11-f112,202311081200-fruit-gh1-c11-f113", f207.note="TRUE", f207.ts=202311081200
MERGE (f206)-[:DEP]->(f207)

MERGE (f208:Precal {name: "precal_m1_1108_202311081200"})
SET f208.key="202311081200-precal-gh1-m1", f208.note="TRUE", f208.ts=202311081200
MERGE (f209:Fruit {name: "fruit1108_202311081200"})
SET f209.key="202311081200-fruit-gh1-c11-f111,202311081200-fruit-gh1-c11-f112,202311081200-fruit-gh1-c11-f113", f209.note="TRUE", f209.ts=202311081200
MERGE (f208)-[:DEP]->(f209)

MERGE (f210:Model {name: "m1ver1106_202311061200"})
SET f210.key="202311061000-model-m1", f210.note="TRUE", f210.ts=202311061200
MERGE (f211:Fruit {name: "fruit1108_202311081200"})
SET f211.key="202311081200-fruit-gh1-c11-f111,202311081200-fruit-gh1-c11-f112,202311081200-fruit-gh1-c11-f113", f211.note="TRUE", f211.ts=202311081200
MERGE (f210)-[:DEP]->(f211)

MERGE (f212:Fruit {name: "fruit1108_202311081200"})
SET f212.key="202311081200-fruit-gh1-c11-f111,202311081200-fruit-gh1-c11-f112,202311081200-fruit-gh1-c11-f113", f212.note="TRUE", f212.ts=202311081200
MERGE (f213:Precal {name: "precal_m2_1108_202311081200"})
SET f213.key="202311081200-precal-gh1-m2", f213.note="TRUE", f213.ts=202311081200
MERGE (f212)-[:INDEP]->(f213)

MERGE (f214:Precal {name: "precal_m2_1108_202311081200"})
SET f214.key="202311081200-precal-gh1-m2", f214.note="TRUE", f214.ts=202311081200
MERGE (f215:Model {name: "m2ver1108_202311081200"})
SET f215.key="202311081000-model-m2", f215.note="TRUE", f215.ts=202311081200
MERGE (f214)-[:INDEP]->(f215)

MERGE (f216:Model {name: "m2ver1108_202311081200"})
SET f216.key="202311081000-model-m2", f216.note="TRUE", f216.ts=202311081200
MERGE (f217:Estproduction {name: "estpro1108_202311081200"})
SET f217.key="202311081200-estproduction-gh1-1110,202311081200-estproduction-gh1-1111,202311081200-estproduction-gh1-1112", f217.note="TRUE", f217.ts=202311081200
MERGE (f216)-[:DEP]->(f217)

MERGE (f218:Fruit {name: "fruit1108_202311081200"})
SET f218.key="202311081200-fruit-gh1-c11-f111,202311081200-fruit-gh1-c11-f112,202311081200-fruit-gh1-c11-f113", f218.note="TRUE", f218.ts=202311081200
MERGE (f219:Estproduction {name: "estpro1108_202311081200"})
SET f219.key="202311081200-estproduction-gh1-1110,202311081200-estproduction-gh1-1111,202311081200-estproduction-gh1-1112", f219.note="TRUE", f219.ts=202311081200
MERGE (f218)-[:DEP]->(f219)

MERGE (f220:Precal {name: "precal_m2_1108_202311081200"})
SET f220.key="202311081200-precal-gh1-m2", f220.note="TRUE", f220.ts=202311081200
MERGE (f221:Estproduction {name: "estpro1108_202311081200"})
SET f221.key="202311081200-estproduction-gh1-1110,202311081200-estproduction-gh1-1111,202311081200-estproduction-gh1-1112", f221.note="TRUE", f221.ts=202311081200
MERGE (f220)-[:DEP]->(f221)

MERGE (f222:Sensor {name: "sensor1108_202311081200"})
SET f222.key="202311081200-sensor-gh1-temp-sen11,202311081200-sensor-gh1-temp-sen12,202311081200-sensor-gh1-humid-sen13,202311081200-sensor-gh1-humid-sen14", f222.note="TRUE", f222.ts=202311081200
MERGE (f223:Precal {name: "precal_m2_1108_202311081200"})
SET f223.key="202311081200-precal-gh1-m2", f223.note="TRUE", f223.ts=202311081200
MERGE (f222)-[:INDEP]->(f223)

MERGE (f224:Sensor {name: "sensor1108_202311081200"})
SET f224.key="202311081200-sensor-gh1-temp-sen11,202311081200-sensor-gh1-temp-sen12,202311081200-sensor-gh1-humid-sen13,202311081200-sensor-gh1-humid-sen14", f224.note="TRUE", f224.ts=202311081200
MERGE (f225:Estproduction {name: "estpro1108_202311081200"})
SET f225.key="202311081200-estproduction-gh1-1110,202311081200-estproduction-gh1-1111,202311081200-estproduction-gh1-1112", f225.note="TRUE", f225.ts=202311081200
MERGE (f224)-[:DEP]->(f225)

MERGE (f226:Sensor {name: "sensor1106_202311061200"})
SET f226.key="202311061200-sensor-gh1-temp-sen11,202311061200-sensor-gh1-temp-sen12,202311061200-sensor-gh1-humid-sen13,202311061200-sensor-gh1-humid-sen14", f226.note="TRUE", f226.ts=202311061200
MERGE (f227:Model {name: "m2ver1108_202311081200"})
SET f227.key="202311081000-model-m2", f227.note="TRUE", f227.ts=202311081200
MERGE (f226)-[:DEP]->(f227)

MERGE (f228:Sensor {name: "sensor1107_202311071200"})
SET f228.key="202311071200-sensor-gh1-temp-sen11,202311071200-sensor-gh1-temp-sen12,202311071200-sensor-gh1-humid-sen13,202311071200-sensor-gh1-humid-sen14", f228.note="TRUE", f228.ts=202311071200
MERGE (f229:Model {name: "m2ver1108_202311081200"})
SET f229.key="202311081000-model-m2", f229.note="TRUE", f229.ts=202311081200
MERGE (f228)-[:DEP]->(f229)

MERGE (f230:Monitor {name: "monitor1101_202311011200"})
SET f230.key="202311011200-monitor-gh1-photo01", f230.ts=202311011200
MERGE (f231:Precal {name: "precal_m1_1101_202311011200"})
SET f231.key="202311011200-precal-gh1-m1", f231.ts=202311011200
MERGE (f230)-[:INDEP]->(f231)

MERGE (f232:Precal {name: "precal_m1_1101_202311011200"})
SET f232.key="202311011200-precal-gh1-m1", f232.ts=202311011200
MERGE (f233:Model {name: "m1ver1101_202311011200"})
SET f233.key="202311011000-model-m1", f233.ts=202311011200
MERGE (f232)-[:INDEP]->(f233)

MERGE (f234:Monitor {name: "monitor1101_202311011200"})
SET f234.key="202311011200-monitor-gh1-photo01", f234.ts=202311011200
MERGE (f235:Fruit {name: "fruit1101_202311011200"})
SET f235.key="202311011200-fruit-gh1-c11-f111,202311011200-fruit-gh1-c11-f112,202311011200-fruit-gh1-c11-f113", f235.ts=202311011200
MERGE (f234)-[:DEP]->(f235)

MERGE (f236:Precal {name: "precal_m1_1101_202311011200"})
SET f236.key="202311011200-precal-gh1-m1", f236.ts=202311011200
MERGE (f237:Fruit {name: "fruit1101_202311011200"})
SET f237.key="202311011200-fruit-gh1-c11-f111,202311011200-fruit-gh1-c11-f112,202311011200-fruit-gh1-c11-f113", f237.ts=202311011200
MERGE (f236)-[:DEP]->(f237)

MERGE (f238:Model {name: "m1ver1101_202311011200"})
SET f238.key="202311011000-model-m1", f238.ts=202311011200
MERGE (f239:Fruit {name: "fruit1101_202311011200"})
SET f239.key="202311011200-fruit-gh1-c11-f111,202311011200-fruit-gh1-c11-f112,202311011200-fruit-gh1-c11-f113", f239.ts=202311011200
MERGE (f238)-[:DEP]->(f239)

MERGE (f240:Sensor {name: "sensor1104_202311061800"})
SET f240.key="202311041200-sensor-gh1-temp-sen11,202311041200-sensor-gh1-temp-sen12,202311041200-sensor-gh1-humid-sen13,202311041200-sensor-gh1-humid-sen14", f240.ts=202311061800
MERGE (f241:Precal {name: "precal_m2_1104_202311041200"})
SET f241.key="202311041200-precal-gh1-m2", f241.ts=202311041200
MERGE (f240)-[:INDEP]->(f241)

MERGE (f242:Sensor {name: "sensor1104_202311061800"})
SET f242.key="202311041200-sensor-gh1-temp-sen11,202311041200-sensor-gh1-temp-sen12,202311041200-sensor-gh1-humid-sen13,202311041200-sensor-gh1-humid-sen14", f242.ts=202311061800
MERGE (f243:Estproduction {name: "estpro1104_202311061800"})
SET f243.key="202311041200-estproduction-gh1-1110,202311041200-estproduction-gh1-1111,202311041200-estproduction-gh1-1112", f243.ts=202311061800
MERGE (f242)-[:DEP]->(f243)

MERGE (f244:Model {name: "m2ver1103_202311031200"})
SET f244.key="202311031000-model-m2", f244.ts=202311031200
MERGE (f245:Estproduction {name: "estpro1104_202311061800"})
SET f245.key="202311041200-estproduction-gh1-1110,202311041200-estproduction-gh1-1111,202311041200-estproduction-gh1-1112", f245.ts=202311061800
MERGE (f244)-[:DEP]->(f245)

MERGE (f246:Fruit {name: "fruit1104_202311041200"})
SET f246.key="202311041200-fruit-gh1-c11-f111,202311041200-fruit-gh1-c11-f112,202311041200-fruit-gh1-c11-f113", f246.ts=202311041200
MERGE (f247:Estproduction {name: "estpro1104_202311061800"})
SET f247.key="202311041200-estproduction-gh1-1110,202311041200-estproduction-gh1-1111,202311041200-estproduction-gh1-1112", f247.ts=202311061800
MERGE (f246)-[:DEP]->(f247)

MERGE (f248:Precal {name: "precal_m2_1104_202311041200"})
SET f248.key="202311041200-precal-gh1-m2", f248.ts=202311041200
MERGE (f249:Estproduction {name: "estpro1104_202311061800"})
SET f249.key="202311041200-estproduction-gh1-1110,202311041200-estproduction-gh1-1111,202311041200-estproduction-gh1-1112", f249.ts=202311061800
MERGE (f248)-[:DEP]->(f249)

MERGE (f250:Precal {name: "precal_m2_1105_202311051200"})
SET f250.key="202311051200-precal-gh1-m2", f250.ts=202311051200
MERGE (f251:Model {name: "m2ver1105_202311061800"})
SET f251.key="202311051000-model-m2", f251.ts=202311061800
MERGE (f250)-[:INDEP]->(f251)

MERGE (f252:Model {name: "m2ver1105_202311061800"})
SET f252.key="202311051000-model-m2", f252.ts=202311061800
MERGE (f253:Estproduction {name: "estpro1105_202311061800"})
SET f253.key="202311051200-estproduction-gh1-1110,202311051200-estproduction-gh1-1111,202311051200-estproduction-gh1-1112", f253.ts=202311061800
MERGE (f252)-[:DEP]->(f253)

MERGE (f254:Sensor {name: "sensor1105_202311051200"})
SET f254.key="202311051200-sensor-gh1-temp-sen11,202311051200-sensor-gh1-temp-sen12,202311051200-sensor-gh1-humid-sen13,202311051200-sensor-gh1-humid-sen14", f254.ts=202311051200
MERGE (f255:Precal {name: "precal_m2_1105_202311051200"})
SET f255.key="202311051200-precal-gh1-m2", f255.ts=202311051200
MERGE (f254)-[:INDEP]->(f255)

MERGE (f256:Sensor {name: "sensor1103_202311031200"})
SET f256.key="202311031200-sensor-gh1-temp-sen11,202311031200-sensor-gh1-temp-sen12,202311031200-sensor-gh1-humid-sen13,202311031200-sensor-gh1-humid-sen14", f256.ts=202311031200
MERGE (f257:Model {name: "m2ver1105_202311061800"})
SET f257.key="202311051000-model-m2", f257.ts=202311061800
MERGE (f256)-[:DEP]->(f257)

MERGE (f258:Sensor {name: "sensor1104_202311061800"})
SET f258.key="202311041200-sensor-gh1-temp-sen11,202311041200-sensor-gh1-temp-sen12,202311041200-sensor-gh1-humid-sen13,202311041200-sensor-gh1-humid-sen14", f258.ts=202311061800
MERGE (f259:Model {name: "m2ver1105_202311061800"})
SET f259.key="202311051000-model-m2", f259.ts=202311061800
MERGE (f258)-[:DEP]->(f259)

MERGE (f260:Fruit {name: "fruit1105_202311051200"})
SET f260.key="202311051200-fruit-gh1-c11-f111,202311051200-fruit-gh1-c11-f112,202311051200-fruit-gh1-c11-f113", f260.ts=202311051200
MERGE (f261:Estproduction {name: "estpro1105_202311061800"})
SET f261.key="202311051200-estproduction-gh1-1110,202311051200-estproduction-gh1-1111,202311051200-estproduction-gh1-1112", f261.ts=202311061800
MERGE (f260)-[:DEP]->(f261)

MERGE (f262:Precal {name: "precal_m2_1105_202311051200"})
SET f262.key="202311051200-precal-gh1-m2", f262.ts=202311051200
MERGE (f263:Estproduction {name: "estpro1105_202311061800"})
SET f263.key="202311051200-estproduction-gh1-1110,202311051200-estproduction-gh1-1111,202311051200-estproduction-gh1-1112", f263.ts=202311061800
MERGE (f262)-[:DEP]->(f263)

MERGE (f264:Sensor {name: "sensor1105_202311051200"})
SET f264.key="202311051200-sensor-gh1-temp-sen11,202311051200-sensor-gh1-temp-sen12,202311051200-sensor-gh1-humid-sen13,202311051200-sensor-gh1-humid-sen14", f264.ts=202311051200
MERGE (f265:Estproduction {name: "estpro1105_202311061800"})
SET f265.key="202311051200-estproduction-gh1-1110,202311051200-estproduction-gh1-1111,202311051200-estproduction-gh1-1112", f265.ts=202311061800
MERGE (f264)-[:DEP]->(f265)

MERGE (f266:Sensor {name: "sensor1104_202311061800"})
SET f266.key="202311041200-sensor-gh1-temp-sen11,202311041200-sensor-gh1-temp-sen12,202311041200-sensor-gh1-humid-sen13,202311041200-sensor-gh1-humid-sen14", f266.ts=202311061800
MERGE (f267:Precal {name: "precal_m2_1104_202311041200"})
SET f267.key="202311041200-precal-gh1-m2", f267.ts=202311041200
MERGE (f266)-[:INDEP]->(f267)

MERGE (f268:Sensor {name: "sensor1104_202311061800"})
SET f268.key="202311041200-sensor-gh1-temp-sen11,202311041200-sensor-gh1-temp-sen12,202311041200-sensor-gh1-humid-sen13,202311041200-sensor-gh1-humid-sen14", f268.ts=202311061800
MERGE (f269:Estproduction {name: "estpro1104_202311061800"})
SET f269.key="202311041200-estproduction-gh1-1110,202311041200-estproduction-gh1-1111,202311041200-estproduction-gh1-1112", f269.ts=202311061800
MERGE (f268)-[:DEP]->(f269)

MERGE (f270:Precal {name: "precal_m2_1105_202311051200"})
SET f270.key="202311051200-precal-gh1-m2", f270.ts=202311051200
MERGE (f271:Model {name: "m2ver1105_202311061800"})
SET f271.key="202311051000-model-m2", f271.ts=202311061800
MERGE (f270)-[:INDEP]->(f271)

MERGE (f272:Model {name: "m2ver1105_202311061800"})
SET f272.key="202311051000-model-m2", f272.ts=202311061800
MERGE (f273:Estproduction {name: "estpro1105_202311061800"})
SET f273.key="202311051200-estproduction-gh1-1110,202311051200-estproduction-gh1-1111,202311051200-estproduction-gh1-1112", f273.ts=202311061800
MERGE (f272)-[:DEP]->(f273)

MERGE (f274:Sensor {name: "sensor1105_202311051200"})
SET f274.key="202311051200-sensor-gh1-temp-sen11,202311051200-sensor-gh1-temp-sen12,202311051200-sensor-gh1-humid-sen13,202311051200-sensor-gh1-humid-sen14", f274.ts=202311051200
MERGE (f275:Precal {name: "precal_m2_1105_202311051200"})
SET f275.key="202311051200-precal-gh1-m2", f275.ts=202311051200
MERGE (f274)-[:INDEP]->(f275)

MERGE (f276:Sensor {name: "sensor1103_202311031200"})
SET f276.key="202311031200-sensor-gh1-temp-sen11,202311031200-sensor-gh1-temp-sen12,202311031200-sensor-gh1-humid-sen13,202311031200-sensor-gh1-humid-sen14", f276.ts=202311031200
MERGE (f277:Model {name: "m2ver1105_202311061800"})
SET f277.key="202311051000-model-m2", f277.ts=202311061800
MERGE (f276)-[:DEP]->(f277)

MERGE (f278:Sensor {name: "sensor1104_202311061800"})
SET f278.key="202311041200-sensor-gh1-temp-sen11,202311041200-sensor-gh1-temp-sen12,202311041200-sensor-gh1-humid-sen13,202311041200-sensor-gh1-humid-sen14", f278.ts=202311061800
MERGE (f279:Model {name: "m2ver1105_202311061800"})
SET f279.key="202311051000-model-m2", f279.ts=202311061800
MERGE (f278)-[:DEP]->(f279)

MERGE (f280:Estproduction {name: "estpro1110_202311101200"})
SET f280.key="202311101200-estproduction-gh1-1113,202311101200-estproduction-gh1-1114,202311101200-estproduction-gh1-1115", f280.note="TRUE", f280.ts=202311101200
MERGE (f281:Fruit {name: "fruit1111_202311131500"})
SET f281.key="202311111200-fruit-gh1-c11-f111,202311111200-fruit-gh1-c11-f112,202311111200-fruit-gh1-c11-f113", f281.note="simulation", f281.ts=202311131500
MERGE (f280)-[:DEP]->(f281)

MERGE (f282:Estproduction {name: "estpro1110_202311101200"})
SET f282.key="202311101200-estproduction-gh1-1113,202311101200-estproduction-gh1-1114,202311101200-estproduction-gh1-1115", f282.note="TRUE", f282.ts=202311101200
MERGE (f283:Precal {name: "precal_simulation_1111_202311131500"})
SET f283.key="202311111200-precal-gh1-simulation", f283.note="simulation", f283.ts=202311131500
MERGE (f282)-[:INDEP]->(f283)

MERGE (f284:Precal {name: "precal_simulation_1111_202311131500"})
SET f284.key="202311111200-precal-gh1-simulation", f284.note="simulation", f284.ts=202311131500
MERGE (f285:Fruit {name: "fruit1111_202311131500"})
SET f285.key="202311111200-fruit-gh1-c11-f111,202311111200-fruit-gh1-c11-f112,202311111200-fruit-gh1-c11-f113", f285.note="simulation", f285.ts=202311131500
MERGE (f284)-[:DEP]->(f285)

MERGE (f286:Fruit {name: "fruit1111_202311131500"})
SET f286.key="202311111200-fruit-gh1-c11-f111,202311111200-fruit-gh1-c11-f112,202311111200-fruit-gh1-c11-f113", f286.note="simulation", f286.ts=202311131500
MERGE (f287:Precal {name: "precal_m2_1112_202311121200"})
SET f287.key="202311121200-precal-gh1-m2", f287.note="TRUE", f287.ts=202311121200
MERGE (f286)-[:INDEP]->(f287)

MERGE (f288:Precal {name: "precal_m2_1112_202311121200"})
SET f288.key="202311121200-precal-gh1-m2", f288.note="TRUE", f288.ts=202311121200
MERGE (f289:Model {name: "m2ver1112_202311121200"})
SET f289.key="202311121000-model-m2", f289.note="TRUE", f289.ts=202311121200
MERGE (f288)-[:INDEP]->(f289)

MERGE (f290:Model {name: "m2ver1112_202311121200"})
SET f290.key="202311121000-model-m2", f290.note="TRUE", f290.ts=202311121200
MERGE (f291:Estproduction {name: "estpro1111_202311131500"})
SET f291.key="202311111200-estproduction-gh1-1113,202311111200-estproduction-gh1-1114,202311111200-estproduction-gh1-1115", f291.note="simulation", f291.ts=202311131500
MERGE (f290)-[:DEP]->(f291)

MERGE (f292:Fruit {name: "fruit1111_202311131500"})
SET f292.key="202311111200-fruit-gh1-c11-f111,202311111200-fruit-gh1-c11-f112,202311111200-fruit-gh1-c11-f113", f292.note="simulation", f292.ts=202311131500
MERGE (f293:Estproduction {name: "estpro1111_202311131500"})
SET f293.key="202311111200-estproduction-gh1-1113,202311111200-estproduction-gh1-1114,202311111200-estproduction-gh1-1115", f293.note="simulation", f293.ts=202311131500
MERGE (f292)-[:DEP]->(f293)

MERGE (f294:Precal {name: "precal_m2_1112_202311121200"})
SET f294.key="202311121200-precal-gh1-m2", f294.note="TRUE", f294.ts=202311121200
MERGE (f295:Estproduction {name: "estpro1111_202311131500"})
SET f295.key="202311111200-estproduction-gh1-1113,202311111200-estproduction-gh1-1114,202311111200-estproduction-gh1-1115", f295.note="simulation", f295.ts=202311131500
MERGE (f294)-[:DEP]->(f295)

MERGE (f296:Sensor {name: "sensor1111_202311111200"})
SET f296.key="202311111200-sensor-gh1-temp-sen11,202311111200-sensor-gh1-temp-sen12,202311111200-sensor-gh1-humid-sen13,202311111200-sensor-gh1-humid-sen14", f296.note="TRUE", f296.ts=202311111200
MERGE (f297:Precal {name: "precal_m2_1112_202311121200"})
SET f297.key="202311121200-precal-gh1-m2", f297.note="TRUE", f297.ts=202311121200
MERGE (f296)-[:INDEP]->(f297)

MERGE (f298:Sensor {name: "sensor1111_202311111200"})
SET f298.key="202311111200-sensor-gh1-temp-sen11,202311111200-sensor-gh1-temp-sen12,202311111200-sensor-gh1-humid-sen13,202311111200-sensor-gh1-humid-sen14", f298.note="TRUE", f298.ts=202311111200
MERGE (f299:Estproduction {name: "estpro1111_202311131500"})
SET f299.key="202311111200-estproduction-gh1-1113,202311111200-estproduction-gh1-1114,202311111200-estproduction-gh1-1115", f299.note="simulation", f299.ts=202311131500
MERGE (f298)-[:DEP]->(f299)

MERGE (f300:Estproduction {name: "estpro1111_202311131500"})
SET f300.key="202311111200-estproduction-gh1-1113,202311111200-estproduction-gh1-1114,202311111200-estproduction-gh1-1115", f300.note="simulation", f300.ts=202311131500
MERGE (f301:Fruit {name: "fruit1112_202311131500"})
SET f301.key="202311121200-fruit-gh1-c11-f111,202311121200-fruit-gh1-c11-f112,202311121200-fruit-gh1-c11-f113", f301.note="simulation", f301.ts=202311131500
MERGE (f300)-[:DEP]->(f301)

MERGE (f302:Estproduction {name: "estpro1111_202311131500"})
SET f302.key="202311111200-estproduction-gh1-1113,202311111200-estproduction-gh1-1114,202311111200-estproduction-gh1-1115", f302.note="simulation", f302.ts=202311131500
MERGE (f303:Precal {name: "precal_simulation_1112_202311131500"})
SET f303.key="202311121200-precal-gh1-simulation", f303.note="simulation", f303.ts=202311131500
MERGE (f302)-[:INDEP]->(f303)

MERGE (f304:Precal {name: "precal_simulation_1112_202311131500"})
SET f304.key="202311121200-precal-gh1-simulation", f304.note="simulation", f304.ts=202311131500
MERGE (f305:Fruit {name: "fruit1112_202311131500"})
SET f305.key="202311121200-fruit-gh1-c11-f111,202311121200-fruit-gh1-c11-f112,202311121200-fruit-gh1-c11-f113", f305.note="simulation", f305.ts=202311131500
MERGE (f304)-[:DEP]->(f305)

MERGE (f306:Fruit {name: "fruit1112_202311131500"})
SET f306.key="202311121200-fruit-gh1-c11-f111,202311121200-fruit-gh1-c11-f112,202311121200-fruit-gh1-c11-f113", f306.note="simulation", f306.ts=202311131500
MERGE (f307:Precal {name: "precal_m2_1112_202311121200"})
SET f307.key="202311121200-precal-gh1-m2", f307.note="TRUE", f307.ts=202311121200
MERGE (f306)-[:INDEP]->(f307)

MERGE (f308:Precal {name: "precal_m2_1112_202311121200"})
SET f308.key="202311121200-precal-gh1-m2", f308.note="TRUE", f308.ts=202311121200
MERGE (f309:Model {name: "m2ver1112_202311121200"})
SET f309.key="202311121000-model-m2", f309.note="TRUE", f309.ts=202311121200
MERGE (f308)-[:INDEP]->(f309)

MERGE (f310:Model {name: "m2ver1112_202311121200"})
SET f310.key="202311121000-model-m2", f310.note="TRUE", f310.ts=202311121200
MERGE (f311:Estproduction {name: "estpro1112_202311131500"})
SET f311.key="202311121200-estproduction-gh1-1113,202311121200-estproduction-gh1-1114,202311121200-estproduction-gh1-1115", f311.note="simulation", f311.ts=202311131500
MERGE (f310)-[:DEP]->(f311)

MERGE (f312:Fruit {name: "fruit1112_202311131500"})
SET f312.key="202311121200-fruit-gh1-c11-f111,202311121200-fruit-gh1-c11-f112,202311121200-fruit-gh1-c11-f113", f312.note="simulation", f312.ts=202311131500
MERGE (f313:Estproduction {name: "estpro1112_202311131500"})
SET f313.key="202311121200-estproduction-gh1-1113,202311121200-estproduction-gh1-1114,202311121200-estproduction-gh1-1115", f313.note="simulation", f313.ts=202311131500
MERGE (f312)-[:DEP]->(f313)

MERGE (f314:Precal {name: "precal_m2_1112_202311121200"})
SET f314.key="202311121200-precal-gh1-m2", f314.note="TRUE", f314.ts=202311121200
MERGE (f315:Estproduction {name: "estpro1112_202311131500"})
SET f315.key="202311121200-estproduction-gh1-1113,202311121200-estproduction-gh1-1114,202311121200-estproduction-gh1-1115", f315.note="simulation", f315.ts=202311131500
MERGE (f314)-[:DEP]->(f315)

MERGE (f316:Sensor {name: "sensor1112_202311121200"})
SET f316.key="202311121200-sensor-gh1-temp-sen11,202311121200-sensor-gh1-temp-sen12,202311121200-sensor-gh1-humid-sen13,202311121200-sensor-gh1-humid-sen14", f316.note="TRUE", f316.ts=202311121200
MERGE (f317:Precal {name: "precal_m2_1112_202311121200"})
SET f317.key="202311121200-precal-gh1-m2", f317.note="TRUE", f317.ts=202311121200
MERGE (f316)-[:INDEP]->(f317)

MERGE (f318:Sensor {name: "sensor1112_202311121200"})
SET f318.key="202311121200-sensor-gh1-temp-sen11,202311121200-sensor-gh1-temp-sen12,202311121200-sensor-gh1-humid-sen13,202311121200-sensor-gh1-humid-sen14", f318.note="TRUE", f318.ts=202311121200
MERGE (f319:Estproduction {name: "estpro1112_202311131500"})
SET f319.key="202311121200-estproduction-gh1-1113,202311121200-estproduction-gh1-1114,202311121200-estproduction-gh1-1115", f319.note="simulation", f319.ts=202311131500
MERGE (f318)-[:DEP]->(f319)

MERGE (f320:Monitor {name: "monitor1102_202311021200"})
SET f320.key="202311021200-monitor-gh1-photo01", f320.ts=202311021200
MERGE (f321:Precal {name: "precal_m1_1102_202311021200"})
SET f321.key="202311021200-precal-gh1-m1", f321.ts=202311021200
MERGE (f320)-[:INDEP]->(f321)

MERGE (f322:Precal {name: "precal_m1_1102_202311021200"})
SET f322.key="202311021200-precal-gh1-m1", f322.ts=202311021200
MERGE (f323:Model {name: "m1ver1102_202311021200"})
SET f323.key="202311021000-model-m1", f323.ts=202311021200
MERGE (f322)-[:INDEP]->(f323)

MERGE (f324:Monitor {name: "monitor1102_202311021200"})
SET f324.key="202311021200-monitor-gh1-photo01", f324.ts=202311021200
MERGE (f325:Fruit {name: "fruit1102_202311021200"})
SET f325.key="202311021200-fruit-gh1-c11-f111,202311021200-fruit-gh1-c11-f112,202311021200-fruit-gh1-c11-f113", f325.ts=202311021200
MERGE (f324)-[:DEP]->(f325)

MERGE (f326:Precal {name: "precal_m1_1102_202311021200"})
SET f326.key="202311021200-precal-gh1-m1", f326.ts=202311021200
MERGE (f327:Fruit {name: "fruit1102_202311021200"})
SET f327.key="202311021200-fruit-gh1-c11-f111,202311021200-fruit-gh1-c11-f112,202311021200-fruit-gh1-c11-f113", f327.ts=202311021200
MERGE (f326)-[:DEP]->(f327)

MERGE (f328:Model {name: "m1ver1102_202311021200"})
SET f328.key="202311021000-model-m1", f328.ts=202311021200
MERGE (f329:Fruit {name: "fruit1102_202311021200"})
SET f329.key="202311021200-fruit-gh1-c11-f111,202311021200-fruit-gh1-c11-f112,202311021200-fruit-gh1-c11-f113", f329.ts=202311021200
MERGE (f328)-[:DEP]->(f329)

