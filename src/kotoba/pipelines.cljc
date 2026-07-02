(ns kotoba.pipelines
  "Render pipelines as portable data.

   This namespace is the .cljc authority for open-world render pipeline specs.
   The Rust fixture/codegen path has been retired; consumers read this EDN-shaped
   table directly from Clojure, ClojureScript, SCI, or kotoba-hosted runtimes.

   Fields per pipeline: :shader, :cull (:back/:front/:none), :depth-write (bool),
   :depth-compare (:less/:less-equal/...), :blend (:none/:alpha).")

(def pipelines
  "The open-world render pipelines as pure data."
  {:terrain    {:shader "scene_terrain"    :cull :back :depth-write true  :depth-compare :less       :blend :none}
   :sky        {:shader "scene_sky"        :cull :none :depth-write false :depth-compare :less-equal :blend :none}
   :vegetation {:shader "scene_vegetation" :cull :none :depth-write true  :depth-compare :less       :blend :alpha}
   :character  {:shader "scene_character"  :cull :back :depth-write true  :depth-compare :less       :blend :none}
   :water      {:shader "scene_water"      :cull :none :depth-write false :depth-compare :less       :blend :alpha}
   :voxel      {:shader "scene_voxel"      :cull :back :depth-write true  :depth-compare :less       :blend :none}
   :particle   {:shader "scene_particle"  :cull :none :depth-write false :depth-compare :less       :blend :alpha}
   :atlas      {:shader "scene_atlas"     :cull :none :depth-write false :depth-compare :less       :blend :alpha}})

(def native-pipelines
  "Compatibility alias for callers that have not renamed yet."
  pipelines)

(def valid-culls #{:back :front :none})
(def valid-depth-compares #{:less :less-equal :greater :greater-equal :equal :always})
(def valid-blends #{:none :alpha})

(defn pipeline?
  [m]
  (and (map? m)
       (string? (:shader m))
       (contains? valid-culls (:cull m))
       (boolean? (:depth-write m))
       (contains? valid-depth-compares (:depth-compare m))
       (contains? valid-blends (:blend m))))

(defn valid?
  "True when every pipeline spec is structurally valid."
  [m]
  (and (map? m)
       (every? keyword? (keys m))
       (every? pipeline? (vals m))))

(defn spec
  "Return one pipeline spec by id."
  [id]
  (get pipelines id))
